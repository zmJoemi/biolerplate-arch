package dev.joemi.arch.data;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public final class DataResult<T> {
    public static final int STATUS_SUCCESS = 1;
    public static final int STATUS_FAILED = -1;

    @Nullable
    private final T data;

    private final boolean isSuccess;

    @Nullable
    private final String message;

    public DataResult(@Nullable T data, boolean isSuccess, @Nullable String message) {
        this.data = data;
        this.isSuccess = isSuccess;
        this.message = message;
    }

    public static <T> DataResult<T> createWithSuccess(T data) {
        return new DataResult<>(data, true, "");
    }

    public static <T> DataResult<T> createWithFailure(T data) {
        return new DataResult<>(data, false, "");
    }

    public static <T> DataResult<T> createWithStatus(boolean isSuccess, String message) {
        return new DataResult<>(null, isSuccess, message);
    }

    @NonNull
    @Override
    public String toString() {
        return "DataResult{" +
                "data=" + data +
                ", isSuccess=" + isSuccess +
                ", message='" + message + '\'' +
                '}';
    }
}
