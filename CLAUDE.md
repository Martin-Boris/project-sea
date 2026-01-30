# CLAUDE.md - AI Assistant Guide for Project-sea

## Project Overview

Project-sea is a multiplayer naval combat game where players control ships on a 2D sea map, targeting and shooting at opponents. It's built with libGDX for clients (web/desktop) and Quarkus for the game server, communicating via WebSocket.

## Codebase Structure

```
project-sea/
├── core/           # Shared game logic (libGDX, GWT-compatible)
├── html/           # Web client (GWT compilation)
├── lwjgl3/         # Desktop client (LWJGL3)
├── server/         # Game server (Quarkus, Maven)
├── assets/         # Game assets (sprites, UI)
└── .github/        # CI/CD workflows
```

### Module Details

**core/** - Main game module (Gradle)
- `src/main/java/com/bmrt/projectsea/domain/` - Domain entities (Ship, SeaMap, GameInstance, Vector, Direction)
- `src/main/java/com/bmrt/projectsea/render/` - LibGDX rendering (actors, camera, UI)
- `src/main/java/com/bmrt/projectsea/websocket/` - WebSocket client adapter
- Uses ports/adapters pattern: `RenderPort`, `WebSocketPort` interfaces

**server/** - Quarkus game server (Maven)
- `src/main/java/com/bmrt/projectsea/domain/` - Server-side domain model
- `src/main/java/com/bmrt/projectsea/application/` - Application services
- `src/main/java/com/bmrt/projectsea/websocket/websocket/` - WebSocket controller
- Helm charts in `project-sea-server/`

**html/** - GWT web client
- Compiles core to JavaScript
- Dockerfile in `src/main/docker/`
- Helm charts in `project-sea-client/`

**lwjgl3/** - Desktop client
- Native desktop launcher
- WebSocket config via system properties

## Build Commands

### Client (Gradle)

```bash
# Run all tests
./gradlew test

# Run desktop client (local dev)
./gradlew lwjgl3:run

# Build web client for distribution
./gradlew html:dist

# Run web client in SuperDev mode (hot reload)
./gradlew html:superDev

# Build desktop JAR
./gradlew lwjgl3:jar

# Clean build
./gradlew clean build
```

### Server (Maven)

```bash
cd server

# Run tests
./mvnw test

# Run in dev mode (hot reload)
./mvnw quarkus:dev

# Build JVM package
./mvnw package

# Build native executable (GraalVM required)
./mvnw package -Dnative
```

## Architecture Patterns

### Hexagonal Architecture (Ports & Adapters)
The domain layer defines interfaces (ports) implemented by infrastructure:
- `RenderPort` - Abstraction for rendering, implemented by `RenderAdapter`
- `WebSocketPort` - Abstraction for network, implemented by `WebsocketController`
- `ClientCommunicationPort` - Server-side client communication abstraction

### WebSocket Protocol
Messages are semicolon-delimited strings: `ACTION;param1;param2;...`

Actions (defined in `Action` enum):
- `JOIN;name` - Player joins the game
- `TURN;direction;name` - Player changes direction (LEFT, RIGHT, TOP, BOT)
- `STOP;name` - Player stops moving
- `SHOOT;shooter;target` - Player shoots at target
- `LEAVE;name` - Player leaves the game

Server responses include ship state: `ACTION;name;x;y;speedX;speedY;direction;hp;maxHp`

### Game Constants (in Ship.java)
- `MAX_HP = 10000` - Ship max health
- `DAMAGE = 2500` - Damage per shot
- `SPEED_TILE_PER_SEC = 4` - Movement speed
- `RANGE = 8` - Shooting range (tiles)
- `GAME_TICK = 1/60f` - Fixed timestep

## Testing Conventions

### Test Structure
- JUnit 5 with Mockito
- Nested test classes named after the method being tested
- Test methods prefixed with `case` describing the scenario

```java
@Nested
class methodName {
    @Test
    void caseNormalBehavior() { ... }

    @Test
    void caseEdgeCase() { ... }
}
```

### Test Builders
Use builder pattern for test data (see `ShipBuilder`):
```java
Ship ship = ShipBuilder.newShip()
    .withName("Test")
    .withPosition(10, 10)
    .withSpeed(0, 0)
    .build();
```

### Running Tests
```bash
# Core tests
./gradlew :core:test

# Server tests
cd server && ./mvnw test
```

## Code Style

### EditorConfig Settings
- Indent: 4 spaces for Java/Kotlin, 2 spaces for Gradle
- Line endings: LF
- Charset: UTF-8
- Trailing whitespace: trim (except .md)

### Java Conventions
- Java 11 source compatibility (core/html/lwjgl3)
- Java 17 for server
- No explicit access modifiers on test methods
- Descriptive enum values (e.g., `Direction.LEFT`, `Action.JOIN`)

## Deployment

### CI/CD (GitHub Actions)
Push to `main` triggers `.github/workflows/deploy.yaml`:
1. Build client (`./gradlew html:dist`) and Docker image
2. Build server native executable (`./mvnw package -Dnative`) and Docker image
3. Deploy to Kubernetes via Helm

### Local Development

**Desktop client → Local server:**
```bash
# Terminal 1: Start server
cd server && ./mvnw quarkus:dev

# Terminal 2: Start desktop client
./gradlew lwjgl3:run
```

**Web client (SuperDev mode):**
```bash
./gradlew html:superDev
# Access at http://localhost:5000
```

### WebSocket Configuration
Desktop client uses system properties (set in `lwjgl3/build.gradle`):
- `websocketUrl` - Server hostname
- `websocketPort` - Server port
- `websocketProtocol` - `ws` or `wss`
- `websocketContextPath` - URL path

## Key Domain Concepts

### Ship
Player-controlled entity with position, speed, direction, and health. Can move, shoot (if in range), and be damaged.

### SeaMap
Bounded game area (25x25 tiles default). Ships cannot move outside boundaries.

### GameInstance
- **Client-side**: Manages local game state, handles user input, delegates to ports
- **Server-side**: Authoritative game state, processes actions, broadcasts updates

### Cooldown
Rate limits shooting (port and starboard cannons have separate cooldowns).

### Direction
Movement directions: `LEFT`, `RIGHT`, `TOP`, `BOT`

## Important Files

- `core/src/main/java/com/bmrt/projectsea/ProjectSeaMain.java` - Main game entry point
- `core/src/main/java/com/bmrt/projectsea/domain/GameInstance.java` - Client game state
- `server/src/main/java/com/bmrt/projectsea/websocket/websocket/GameController.java` - WebSocket handler
- `server/src/main/java/com/bmrt/projectsea/domain/GameInstance.java` - Server game state
- `gradle.properties` - Version configurations
- `server/pom.xml` - Server dependencies (Quarkus 3.16.3)

## Dependencies

### Core Client
- libGDX 1.12.1
- gdx-websockets 1.9.10.3
- ShapeDrawer 2.5.0

### Server
- Quarkus 3.16.3
- quarkus-websockets-next
- quarkus-smallrye-health

### Testing
- JUnit 5
- Mockito

## Tips for AI Assistants

1. **Run tests after changes**: Always run `./gradlew test` or `./mvnw test` after modifications
2. **Check both client and server**: Domain classes exist in both `core/` and `server/` with similar but not identical implementations
3. **GWT compatibility**: Code in `core/` must be GWT-compatible (no Java 8+ streams in domain, limited reflection)
4. **Native build**: Server uses GraalVM native-image; avoid reflection-heavy patterns
5. **WebSocket messages**: Follow the semicolon-delimited format strictly
6. **Cooldown system**: Port and starboard cannons have independent cooldowns
7. **Fixed timestep**: Game physics use `GAME_TICK` (1/60s) fixed timestep with accumulator pattern
