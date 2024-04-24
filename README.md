# Description

This project demonstrates performance difference between Kotlin and C++ implementations regarding SQLite operations on iOS and Android.

It contains Kotlin Multiplatform Project including [SQLDelight](https://github.com/cashapp/sqldelight) and an Swift XCode project with framework containing C++ SQLite wrapper (which also includes Objective C wrapper for Swift - C++ interop).

Both Kotlin and C++ use the same [SQLChipher](https://github.com/sqlcipher/sqlcipher) 3.4.2 linked to XCode project via Cocoapods.

Performance test includes bulk inserts of 100 000 entities (using `INSERT`) and reading them into native structures afterwards (using `SELECT`)

## iOS: perfomance results using _iPhone SE_ and _SQLDelight 1.5.0_

### Debug configuration

|  Op      | Kotlin  | C++         |
| -------- | ------- | ----------- |
| `INSERT` | ~17sec  | **~3sec**   |
| `SELECT` | ~3sec   | **~0.4sec** |

### Release configuration

|  Op      | Kotlin   | C++          |
| -------- | -------- | ------------ |
| `INSERT` | ~4.7sec  | **~0.75sec** |
| `SELECT` | ~0.77sec | **~0.15sec** |

## Update 2024 - Performance results using _iPhone 8_ and _SQLDelight 2.0.2_

### Debug configuration

|  Op      | Kotlin  | C++         |
| -------- | ------- | ----------- |
| `INSERT` | ~2.35sec | **~0.87sec** |
| `SELECT` | ~1.08sec | **~0.13sec** |

### Release configuration

|  Op      | Kotlin*  | C++          |
| -------- | -------- | ------------ |
| `INSERT` | ~0.47sec | **~0.22sec** |
| `SELECT` | ~0.46sec | **~0.05sec** |

\* There is also a comparison of **Release** performance SQLDelight 1.5.0 vs 2.0.2, where 2.0.2 is faster on `INSERT` and 1.5.0 is faster on `SELECT`:

|  Op      | 1.5.0    | 2.0.2        |
| -------- | -------- | ------------ |
| `INSERT` | ~0.79sec | **~0.47sec** |
| `SELECT` | **~0.27sec** | ~0.46sec |

## Android: performance results using _Nexus 5 (API 24) Simulator_ and _SQLDelight 2.0.2_

### TODO: add results
