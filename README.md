# Description

This project demonstrates performance difference regarding SQLite operations between Kotlin and C++ implementations on iOS.

It contains Kotlin Multiplatform Project including [SQLDelight](https://github.com/cashapp/sqldelight) and an Swift XCode project with framework containing C++ SQLite wrapper (which also includes Objective C wrapper for Swift - C++ interop).

Both Kotlin and C++ use the same [SQLChipher](https://github.com/sqlcipher/sqlcipher) 3.4.2 linked to XCode project via Cocoapods.

Performance test includes bulk inserts of 100 000 entities (using `INSERT`) and reading them into native structures afterwards (using `SELECT`)

## Perfomance results using _iPhone SE_ with Debug configuration

|  Op      | Kotlin | C++         |
| -------- | -------| ----------- |
| `INSERT` | ~17sec | **~3sec**   |
| `SELECT` | ~3sec  | **~0.4sec** |

