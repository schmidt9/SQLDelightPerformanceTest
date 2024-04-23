LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

# c flags
APP_CFLAGS := \
        -DHAVE_MOBILE_OS \
        -DHAVE_LIBC_SYSTEM_PROPERTIES \
        -D_STLP_USE_MALLOC=1 \
        -D_STLP_USE_NO_IOSTREAMS=1 \
        -DHAS_REMOTE_API=0 \
        -I$(LOCAL_PATH) \
        -UNDEBUG \
        -D_FILE_OFFSET_BITS=32 \
        -DSQLITE_HAS_CODEC

# module name
LOCAL_MODULE := SQLCipherTest

# included directories
LOCAL_C_INCLUDES := \
        $(LOCAL_PATH)/SQLCipherTest \
        $(LOCAL_PATH)/SQLCipherTest/sqlite3

# included files
LOCAL_SRC_FILES := \
        $(LOCAL_PATH)/SQLCipherTest/sqlite3/sqlite3.c \
        $(LOCAL_PATH)/SQLCipherTest/CppTestDatabase.cpp \
        $(LOCAL_PATH)/SQLCipherTest/TestDatabase.cpp

# static libraries
LOCAL_STATIC_LIBRARIES := \
        crypto

# ld libs
LOCAL_LDLIBS := \
        -latomic \
        -landroid \
        -llog \
        -lz \
        -lssl \
        -lcrypto

# openssl version
OPENSSL := openssl-1.1.0f

# frameworks paths
OPENSSL_PATH := $(LOCAL_PATH)/SQLCipherTest/$(OPENSSL)

# ld flags
LOCAL_LDFLAGS := \
        -L$(OPENSSL_PATH)/$(TARGET_ARCH_ABI)/lib

include $(BUILD_SHARED_LIBRARY)

# crypto library
include $(CLEAR_VARS)
LOCAL_MODULE := crypto
LOCAL_EXPORT_C_INCLUDES := $(OPENSSL_PATH)/$(TARGET_ARCH_ABI)/include
LOCAL_SRC_FILES := $(OPENSSL_PATH)/$(TARGET_ARCH_ABI)/lib/libcrypto.a
include $(PREBUILT_STATIC_LIBRARY)
