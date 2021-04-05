# Description

This project demonstrates performance difference between Kotlin and C++ implementations regarding SQLite operations on iOS.

It contains Kotlin Multiplatform Project including [SQLDelight](https://github.com/cashapp/sqldelight) and an Swift XCode project with framework containing C++ SQLite wrapper (which also includes Objective C wrapper for Swift - C++ interop).

Both Kotlin and C++ use the same [SQLChipher](https://github.com/sqlcipher/sqlcipher) 3.4.2 linked to XCode project via Cocoapods.

Performance test includes bulk inserts of 100 000 entities (using `INSERT`) and reading them into native structures afterwards (using `SELECT`)

## Perfomance results using _iPhone SE_ 

### Debug configuration

|  Op      | Kotlin  | C++         |
| -------- | ------- | ----------- |
| `INSERT` | ~17sec  | **~3sec**   |
| `SELECT` | ~3sec   | **~0.4sec** |

### Release configuration

|  Op      | Kotlin   | C++          |
| -------- | -------- | ------------ |
| `INSERT` | ~4.7sec  | **~0.75sec** |
| `SELECT` | ~0.77sec | **~0.15sec**  |
