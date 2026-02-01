# Project-sea

A [libGDX](https://libgdx.com/) project generated with [gdx-liftoff](https://github.com/libgdx/gdx-liftoff).

This project was generated with a template including simple application launchers and an `ApplicationAdapter` extension that draws libGDX logo.

## Platforms

- `core`: Main module with the application logic shared by all platforms.
- `lwjgl3`: Primary desktop platform using LWJGL3; was called 'desktop' in older docs.
- `teavm`: Web backend that supports most JVM languages.
- `server`: A separate application without access to the `core` module.

## Gradle

This project uses [Gradle](https://gradle.org/) to manage dependencies.
The Gradle wrapper was included, so you can run Gradle tasks using `gradlew.bat` or `./gradlew` commands.
Useful Gradle tasks and flags:

- `--continue`: when using this flag, errors will not stop the tasks from running.
- `--daemon`: thanks to this flag, Gradle daemon will be used to run chosen tasks.
- `--offline`: when using this flag, cached dependency archives will be used.
- `--refresh-dependencies`: this flag forces validation of all dependencies. Useful for snapshot versions.
- `build`: builds sources and archives of every project.
- `cleanEclipse`: removes Eclipse project data.
- `cleanIdea`: removes IntelliJ project data.
- `clean`: removes `build` folders, which store compiled classes and built archives.
- `eclipse`: generates Eclipse project data.
- `idea`: generates IntelliJ project data.
- `lwjgl3:jar`: builds application's runnable jar, which can be found at `lwjgl3/build/libs`.
- `server:run`: runs the server application.
- `test`: runs unit tests (if any).

## Configuration

Both LWJGL3 (desktop) and TeaVM (web) use shared configuration files in the project root:
- `config-dev.properties` - development settings
- `config-prod.properties` - production settings

**Config file format:**
```properties
websocketUrl=
websocketPort=
websocketProtocol=
websocketContextPath=
```

### Running with dev config (default)

```bash
./gradlew lwjgl3:run
./gradlew teavm:run
```

### Running with prod config

```bash
./gradlew -Penv=prod lwjgl3:run
./gradlew -Penv=prod teavm:run
```

### Building for production

```bash
./gradlew -Penv=prod lwjgl3:jar
./gradlew -Penv=prod teavm:build
```

The TeaVM built application will be in `teavm/build/dist/webapp/`.

Note that most tasks that are not specific to a single project can be run with `name:` prefix, where the `name` should be replaced with the ID of a specific project.
For example, `core:clean` removes `build` folder only from the `core` project.
