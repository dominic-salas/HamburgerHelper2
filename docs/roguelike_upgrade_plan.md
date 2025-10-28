# Roguelike Upgrade Architecture & Roadmap

## Part 1: Architecture Blueprint

### Technology Stack Recommendation

| Option | Rendering & Tooling | Performance Considerations | Migration Complexity | Recommendation |
| --- | --- | --- | --- | --- |
| **Remain on JavaFX** | Replace SceneGraph rendering with `Canvas` + custom render loop via `AnimationTimer`. Integrate third-party particle libs (e.g., FXGL) or lightweight shader-like effects via blend modes. | Manual batching by drawing sprites on single `GraphicsContext`. Use object pools and loose quad-tree for collisions. Proven 60fps with tuned loop and careful allocation. | Very low (0-1 week) because current codebase already runs in JavaFX. Focus shifts to optimization refactor instead of port. | **Recommended**—fastest path, minimal risk, preserves existing UI tooling. |
| **libGDX (Java)** | Full game framework with SpriteBatch, particle editor, shader pipeline. Requires asset pipeline migration. | Excellent performance and control, but adds build tooling and API learning curve. | Moderate (3-5 weeks) to port loop, input, rendering, assets. | Consider only if JavaFX optimization fails or future mobile ports are required. |
| **MonoGame (C#)** | Comparable benefits in C#. Demands new language/runtime proficiency and new tooling. | Strong performance, but requires rebuilding all systems. | High (5-7 weeks) due to language + tooling switch. | Least viable for solo dev under current timeline. |

**Decision:** Stay on **JavaFX** and refactor rendering into a single-canvas, game-loop-driven architecture. This preserves existing code/assets while delivering required performance through pooling, batching, and careful memory management.

#### JavaFX Optimization Pillars
- **Manual Game Loop:** Use `AnimationTimer` (or a scheduled executor for logic) to decouple updates from JavaFX's pulse rate. Maintain a fixed-step logic update and interpolate rendering for smoothness.
- **Single Canvas Rendering:** Draw all gameplay sprites, particles, and screen-space effects onto one `Canvas` per layer (gameplay + UI) to avoid scene graph overhead.
- **Texture Atlases:** Pack sprites into a `WritableImage` atlas and blit via `drawImage` with source rectangles to minimize texture binds.
- **Entity & Projectile Pools:** Recycle objects and maintain component data in primitive arrays to avoid GC stalls.
- **Spatial Partitioning:** Use uniform grids/quadtrees for collision checks to keep the O(n²) explosion in check at 500+ entities.
- **Async Asset Loading:** Load sounds/textures on background threads, pushing results back to FX Application Thread safely for hitch-free menus.

### Core Systems Architecture Overview

```
+-------------------+        +--------------------------+        +-------------------------+
| Input & Core Loop | -----> | Entity Component System  | <----> |  Resource/Asset Manager |
+-------------------+        +--------------------------+        +-------------------------+
          |                            |                                    |
          v                            v                                    v
+-------------------+       +-------------------------+          +--------------------------+
| Player Controller | <---> | Weapon System (Auto)    | <------> |  Upgrade Data Repository |
+-------------------+       +-------------------------+          +--------------------------+
          |                            |                                    |
          v                            v                                    v
+-------------------+       +-------------------------+          +--------------------------+
| Experience System | --->  | Level-Up Choice Engine  | <------> | Synergy/Rules Definitions|
+-------------------+       +-------------------------+          +--------------------------+
          |                            |                                    |
          v                            v                                    v
+-------------------+       +-------------------------+          +--------------------------+
| Meta Progression  | <---- | Run Summary / Rewards   | <------> | Save/Load Persistence    |
+-------------------+       +-------------------------+          +--------------------------+
```

**Key Interaction Notes**
- **Weapon System** pulls base/evolution stats from the **Upgrade Data Repository**, schedules attacks via the ECS, and instantiates projectiles through the **Entity Manager**.
- **Experience System** awards level-ups, which ask the **Level-Up Choice Engine** for weighted options based on owned weapons, synergies, and meta-unlocks.
- **Meta Progression** consumes run summaries, updates permanent unlocks, and persists to disk through the Save/Load layer.
- **Spawn Director** (nested inside ECS systems) orchestrates enemy waves, elites, and bosses based on time, player power, and map configuration.

### Data Structure Recommendations

- **Weapon Trees**: JSON/TSV definitions parsed into `WeaponDefinition` objects.
  ```json
  {
    "id": "whip",
    "name": "Blood Lash",
    "baseStats": { "damage": 15, "cooldown": 1.5, "projectiles": 1, "area": 1.0 },
    "levels": [
      { "damage": 20, "cooldown": 1.3 },
      { "damage": 25, "projectiles": 2 },
      { "evolution": "bloody_storm" }
    ],
    "evolutions": {
      "bloody_storm": {
        "requirements": ["whip_lv4", "relic_heart"],
        "modifiers": { "damage": 60, "cooldown": 1.2, "projectiles": 4, "area": 1.6 },
        "onHitEffects": ["bleed"],
        "visual": "effects/whip_evo.pfx"
      }
    }
  }
  ```
- **Character Stats**: Simple data class with base stats and passives stored in JSON/CSV.
  ```json
  {
    "id": "arcanist",
    "baseStats": { "maxHealth": 120, "moveSpeed": 1.0, "magnet": 1.2 },
    "startingWeapon": "arc_bolt",
    "passives": [
      { "type": "cooldown_multiplier", "value": 0.9 },
      { "type": "exp_gain", "value": 1.15 }
    ]
  }
  ```
- **Progression Tables**: Use flat files (JSON/CSV) for XP thresholds, enemy HP curves, meta-shop costs. Load once at startup into typed structs.
- **Synergy Rules**: Tag-based system (e.g., `tags: ["fire", "area"]`). `LevelUpChoiceEngine` uses tags to surface synergistic upgrades.
- **Entity Data**: Component definitions (Position, Velocity, Health, AIState) stored in contiguous arrays for cache-friendly iteration.

## Part 2: Implementation Roadmap

### Phase 1 – MVP (6-8 weeks)
1. **Rendering Core Refactor (1-1.5 weeks)**
   - Move gameplay rendering to a dedicated `Canvas` with manual game loop (`AnimationTimer`).
   - Introduce fixed-time-step update with accumulator for deterministic combat.
   - Implement pooled sprite draw list and lightweight particle helper.
2. **Core Combat Loop (2 weeks)**
   - Implement auto-fire weapons (3 starter weapons), projectile behaviors, damage numbers.
   - Basic enemy spawner with scaling waves and pooled enemies/projectiles.
3. **Run Flow & XP (1-1.5 weeks)**
   - Experience orbs, pickup radius, level-ups granting stat boosts/weapon levels.
   - Add magnetic pickup upgrade tiers.
4. **Basic Meta Shell (1 week)**
   - Persistent currency, simple upgrade menu with handful of upgrades.

**Critical Path:** Canvas refactor → ECS foundation → Weapon system → XP/Level-up → Meta currency.

### Phase 2 – Feature Complete (8-10 weeks)
1. **Content Expansion (3 weeks)**
   - 6-8 weapons with evolution trees, 4-6 characters with passives.
   - Enemy variety including elites and first boss.
2. **Procedural Systems (2 weeks)**
   - Spawn director with density curves, elite/boss triggers.
   - RNG level-up choices with synergy weighting.
3. **Meta Progression Depth (2 weeks)**
   - Achievements/unlocks, upgrade shop tiers, save/load robustness.
4. **Map/Arena Variety (1-2 weeks)**
   - 2-3 arenas or procedural tiles with performance-friendly layout.

**Critical Path:** Weapon content → Level-up choice engine → Spawn director → Meta unlocks.

### Phase 3 – Polish & Optimization (4-5 weeks)
1. **Performance Tuning (1-2 weeks)**
   - Profiling, entity pooling, spatial hashing, GPU particle tuning.
2. **Visual Juice (1 week)**
   - Screen shake via transient translation/rotation on the gameplay `Canvas` node (small, medium, large presets).
   - Particle bursts using pooled sprite quads rendered with additive `BlendMode` for hits, level-ups, pickups.
   - Lightweight post-processing by compositing glow sprites or duplicating bright layers with blur shader from JavaFX `ColorAdjust`/`Bloom` effects.
   - Ensure UI overlay remains anchored using separate scene graph layer for HUD.
3. **UX & QA (1-2 weeks)**
   - Balancing passes, achievement polish, Steam integration prep.

**Nice-to-Haves:** Additional weapons/characters, daily challenges, leaderboard integration.

## Part 3: Code Foundations

### Weapon Evolution System (Kotlin/Java Pseudocode)
```java
public final class WeaponDefinition {
    public final String id;
    public final WeaponStats base;
    public final List<WeaponLevel> levels;
    public final Map<String, WeaponEvolution> evolutions;
    // constructor omitted
}

public record WeaponStats(float damage, float cooldown, int projectiles, float area, float duration) {}

public record WeaponLevel(Map<String, Float> statModifiers, Optional<String> evolutionId) {}

public record WeaponEvolution(List<String> requirements, WeaponStats modifiers, List<String> onHitEffects) {}

public final class WeaponState {
    private WeaponDefinition def;
    private int level;
    private Optional<String> evolution;

    public void levelUp(PlayerContext ctx) {
        if (evolution.isPresent()) return;
        WeaponLevel levelData = def.levels().get(level);
        applyModifiers(levelData.statModifiers());
        level++;
        levelData.evolutionId().ifPresent(evoId -> {
            if (ctx.meetsRequirements(def.evolutions().get(evoId).requirements())) {
                evolve(evoId);
            }
        });
    }

    private void evolve(String evoId) {
        evolution = Optional.of(evoId);
        WeaponEvolution evo = def.evolutions().get(evoId);
        applyEvolution(evo);
    }

    private void applyModifiers(Map<String, Float> modifiers) {
        modifiers.forEach((stat, value) -> ctx().stats().modify(stat, value));
    }

    private void applyEvolution(WeaponEvolution evo) {
        ctx().stats().overrideWith(evo.modifiers());
        ctx().effects().enable(evo.onHitEffects());
    }
}
```

### Level-Up Choice Generator
```java
public final class LevelUpChoiceEngine {
    private final Random rng;
    private final List<UpgradeCard> allCards;
    private final SynergyIndex synergyIndex;

    public List<UpgradeCard> rollChoices(PlayerState state) {
        List<UpgradeCard> pool = new ArrayList<>();

        // Eligibility filtering
        for (UpgradeCard card : allCards) {
            if (!card.isUnlocked() || !card.requirementsMet(state)) continue;
            pool.add(card.withWeight(weightFor(card, state)));
        }

        return WeightedRandom.pickDistinct(pool, 3, rng);
    }

    private float weightFor(UpgradeCard card, PlayerState state) {
        float weight = card.baseWeight();
        weight *= synergyIndex.matchScore(card.tags(), state.ownedTags());
        weight *= state.isWeaponMaxed(card.weaponId()) ? 0.2f : 1.0f;
        weight *= state.hasEvolutionReady(card.weaponId()) ? 1.5f : 1.0f;
        return weight;
    }
}
```

### Meta-Progression Save/Load (JSON via Java Preferences)
```java
import java.util.prefs.Preferences;

public final class MetaProgressionStore {
    private static final Preferences PREFS = Preferences.userRoot().node("meta_progression");
    private final Gson gson = new Gson();

    public MetaProfile load() {
        String json = PREFS.get("profile", null);
        if (json == null || json.isEmpty()) return MetaProfile.empty();
        return gson.fromJson(json, MetaProfile.class);
    }

    public void save(MetaProfile profile) {
        PREFS.put("profile", gson.toJson(profile));
        PREFS.flush();
    }
}

public record MetaProfile(
    int totalRuns,
    int metaCurrency,
    Set<String> unlockedWeapons,
    Set<String> unlockedCharacters,
    Map<String, Integer> shopUpgrades
) {
    public static MetaProfile empty() {
        return new MetaProfile(0, 0, new HashSet<>(), new HashSet<>(), new HashMap<>());
    }
}
```

### Performance-Optimized Entity Manager
```java
public final class EntityManager {
    private static final int MAX_ENTITIES = 2000;

    private final int[] freeStack = new int[MAX_ENTITIES];
    private int freeTop = 0;
    private int nextId = 0;

    private final boolean[] active = new boolean[MAX_ENTITIES];
    private final float[] posX = new float[MAX_ENTITIES];
    private final float[] posY = new float[MAX_ENTITIES];
    private final float[] velX = new float[MAX_ENTITIES];
    private final float[] velY = new float[MAX_ENTITIES];
    private final float[] radius = new float[MAX_ENTITIES];
    private final int[] health = new int[MAX_ENTITIES];

    public int createEntity() {
        int id = freeTop > 0 ? freeStack[--freeTop] : nextId++;
        if (id >= MAX_ENTITIES) throw new IllegalStateException("Entity pool exhausted");
        active[id] = true;
        return id;
    }

    public void destroyEntity(int id) {
        if (!active[id]) return;
        active[id] = false;
        freeStack[freeTop++] = id;
    }

    public void updatePositions(float delta) {
        for (int id = 0; id < nextId; id++) {
            if (!active[id]) continue;
            posX[id] += velX[id] * delta;
            posY[id] += velY[id] * delta;
        }
    }

    public IntList queryCircle(float x, float y, float r, IntList out) {
        out.clear();
        float r2 = r * r;
        for (int id = 0; id < nextId; id++) {
            if (!active[id]) continue;
            float dx = posX[id] - x;
            float dy = posY[id] - y;
            if (dx * dx + dy * dy <= r2) out.add(id);
        }
        return out;
    }
}

public final class IntList {
    private final int[] data;
    private int size = 0;

    public IntList(int capacity) { this.data = new int[capacity]; }

    public void clear() { size = 0; }

    public void add(int value) { data[size++] = value; }

    public int size() { return size; }

    public int get(int index) { return data[index]; }
}
```
Enhance with spatial hashing or quadtrees during optimization phase (e.g., maintain a secondary grid map keyed by cell index for near-neighbor lookups).

## Part 4: Balancing Framework

### Weapon Scaling Formulas
- **Base Damage:** `damage(level) = baseDamage * (1 + 0.15 * (level - 1))`
- **Cooldown:** `cooldown(level) = max(baseCooldown * 0.92^(level - 1), minCooldown)`
- **Projectiles:** Increase every 2 levels or via specific upgrades.
- **Area:** `area(level) = baseArea * (1 + 0.1 * (level - 1))`
- **Evolution Bonuses:** +50-80% damage, +2 projectiles, unique effects (bleed, burn, chain lightning).

### Enemy Health & Spawn Rates
- **Health Curve:** `hp(t) = baseHP * (1 + 0.12 * minute + 0.02 * minute^2)` where `minute` is run time in minutes.
- **Elite Modifier:** `hp_elite = hp * 6`, damage x2, spawn every 2 minutes after minute 5.
- **Boss Modifier:** `hp_boss = hp * (20 + minute)`, damage x3, unique patterns.
- **Spawn Density:** `spawnRate(t) = baseRate * (1 + 0.15 * minute)`, cap to maintain 500-700 active enemies.

### Experience Requirements
- XP per level: `xp(n) = 20 * n + 5 * n^2` (smooth early levels, escalating late-game).
- Adjust via magnet upgrades and meta bonuses to maintain level-up every ~30-45 seconds early, slowing to ~90 seconds late-run.

### Meta-Currency Earnings
- Base reward: `meta = floor(runMinutes * difficultyMultiplier * 10)`.
- **Difficulty Multiplier:** Normal = 1.0, Hard = 1.3, Endless = 1.5.
- Bonuses for achievements completed during run (+10 each) and boss kills (+15 each).
- Shop upgrade costs escalate exponentially: `cost(rank) = baseCost * 1.8^(rank-1)` to stretch long-term goals without hard wall.

---

**Usage Notes**
- Balance iterations should log run stats (level reached, DPS, damage taken) to CSV for tuning.
- Implement developer cheats (fast XP, skip time) for quicker iteration.

