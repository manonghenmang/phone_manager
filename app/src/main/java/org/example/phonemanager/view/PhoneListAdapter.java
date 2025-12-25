package org.example.phonemanager.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.example.phonemanager.R;
import org.example.phonemanager.model.Phone;

import java.util.ArrayList;
import java.util.List;

public class PhoneListAdapter extends BaseAdapter {
    private Context context;
    private List<Phone> phoneList;
    private List<Phone> filteredList;
    private OnItemClickListener onItemClickListener;

    // 定义点击事件接口
    public interface OnItemClickListener {
        void onItemClick(Phone phone, int position);
    }

    // 设置点击事件监听器
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public PhoneListAdapter(Context context, List<Phone> phoneList) {
        this.context = context;
        this.phoneList = phoneList;
        this.filteredList = new ArrayList<>(phoneList);
    }

    @Override
    public int getCount() {
        return filteredList.size();
    }

    @Override
    public Object getItem(int position) {
        return filteredList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        // 使用ViewHolder模式优化性能
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.phone_list_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.textViewPhoneId = convertView.findViewById(R.id.textViewPhoneId);
            viewHolder.textViewBrand = convertView.findViewById(R.id.textViewBrand);
            viewHolder.textViewModel = convertView.findViewById(R.id.textViewModel);
            viewHolder.textViewOwner = convertView.findViewById(R.id.textViewOwner);
            viewHolder.textViewBorrower = convertView.findViewById(R.id.textViewBorrower);
            viewHolder.textViewReturnPerson = convertView.findViewById(R.id.textViewReturnPerson);
            viewHolder.textViewStatus = convertView.findViewById(R.id.textViewStatus);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // 获取当前手机信息
        Phone phone = filteredList.get(position);

        // 设置数据到视图
        viewHolder.textViewPhoneId.setText("手机编号: " + phone.getPhoneId());
        viewHolder.textViewBrand.setText("品牌: " + (phone.getBrand() != null ? phone.getBrand() : ""));
        viewHolder.textViewModel.setText("型号: " + (phone.getModel() != null ? phone.getModel() : ""));
        viewHolder.textViewOwner.setText("拥有者: " + (phone.getOwner() != null ? phone.getOwner() : ""));
        viewHolder.textViewBorrower.setText("借出人: " + (phone.getBorrower() != null ? phone.getBorrower() : ""));
        viewHolder.textViewReturnPerson.setText("归还人: " + (phone.getReturnPerson() != null ? phone.getReturnPerson() : ""));
        viewHolder.textViewStatus.setText("状态: " + (phone.getStatus() != null ? phone.getStatus() : ""));

        // 设置点击事件
        final int itemPosition = position;
        convertView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(phone, itemPosition);
            }
        });

        return convertView;
    }

    /**
     * 过滤手机列表
     * @param filteredList 过滤后的列表
     */
    public void filterList(List<Phone> filteredList) {
        this.filteredList = filteredList;
        notifyDataSetChanged();
    }

    /**
     * ViewHolder类，用于缓存视图组件
     */
    static class ViewHolder {
        TextView textViewPhoneId;
        TextView textViewBrand;
        TextView textViewModel;
        TextView textViewOwner;
        TextView textViewBorrower;
        TextView textViewReturnPerson;
        TextView textViewStatus;
    }
}