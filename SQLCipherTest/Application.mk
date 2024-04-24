# The ARMv7 is significanly faster due to the use of the hardware FPU
APP_PLATFORM := android-14
APP_STL := c++_static
APP_CPPFLAGS := \
        -frtti \
        -fexceptions \
        -Wall \
        -std=c++17
