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

    public DataResult(boolean isSuccess, @Nullable String message, @Nullable T data) {
        this.isSuccess = isSuccess;
        this.message = message;
        this.data = data;
    }

    public static <T> DataResult<T> createWithSuccess(T data) {
        return new DataResult<>(true, "", data);
    }

    public static <T> DataResult<T> createWithFailure(String message) {
        return new DataResult<>(false, message, null);
    }

    public static <T> DataResult<T> createWithStatus(boolean isSuccess, String message) {
        return new DataResult<>(isSuccess, message, null);
    }

    @Nullable
    public T getData() {
        return data;
    }

    @Nullable
    public String getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return isSuccess;
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
