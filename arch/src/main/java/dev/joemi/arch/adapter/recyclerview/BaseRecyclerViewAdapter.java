package dev.joemi.arch.adapter.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import com.blankj.utilcode.util.ClickUtils;

import dev.joemi.arch.R;

public abstract class BaseRecyclerViewAdapter<M, B extends ViewBinding> extends ListAdapter<M, RecyclerView.ViewHolder> {

    private final LayoutInflater mLayoutInflater;
    private final Context mContext;

    private final View.OnClickListener mOnClickListener;

    protected OnItemClickListener<M> mOnItemClickListener;

    protected BaseRecyclerViewAdapter(@NonNull Context context, @NonNull DiffUtil.ItemCallback<M> diffCallback) {
        super(diffCallback);
        this.mContext = context;
        this.mLayoutInflater = LayoutInflater.from(context);
        this.mOnClickListener = v -> {
            if (mOnItemClickListener != null) {
                int position = (int) v.getTag(R.id.list_item_position);
                mOnItemClickListener.onItemClick(v, getItem(position), position);
            }
        };
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        B binding = getViewBinding(mLayoutInflater, parent, viewType);
        BaseBindingViewHolder holder = new BaseBindingViewHolder(binding);
        holder.itemView.setTag(R.id.list_item_position, holder.getAdapterPosition());
        ClickUtils.applySingleDebouncing(holder.itemView, this.mOnClickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        BaseBindingViewHolder _holder = (BaseBindingViewHolder) holder;
        //noinspection unchecked
        B binding = (B) _holder.getBinding();
        this.onBindItem(binding, getItem(position), holder);
    }

    protected abstract @NonNull B getViewBinding(LayoutInflater from, ViewGroup parent, int viewType);

    protected abstract void onBindItem(B binding, M item, RecyclerView.ViewHolder holder);

    public void setOnItemClickListener(OnItemClickListener<M> listener) {
        mOnItemClickListener = listener;
    }

    public static class BaseBindingViewHolder extends RecyclerView.ViewHolder {
        private final ViewBinding _binding;

        BaseBindingViewHolder(ViewBinding binding) {
            super(binding.getRoot());
            _binding = binding;
        }

        public ViewBinding getBinding() {
            return _binding;
        }
    }

    public interface OnItemClickListener<M> {
        void onItemClick(View itemView, M item, int position);
    }
}
