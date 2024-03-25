package mobileworld.event;

import java.util.ArrayList;
import java.util.List;

public interface DataChangeListener {

    void onDataChange();

    // Phương thức mặc định để thiết lập sự kiện
    default void setEventDataChangeListener(DataChangeListener changeListener) {
        
    }

    // Danh sách lưu trữ các lắng nghe sự kiện
    List<DataChangeListener> dataChangeListeners = new ArrayList<>();

    // Phương thức mặc định để thêm một lắng nghe sự kiện mới
    default void addDataChangeListener(DataChangeListener listener) {
        dataChangeListeners.add(listener);
    }

    // Phương thức mặc định để loại bỏ một lắng nghe sự kiện
    default void removeDataChangeListener(DataChangeListener listener) {
        dataChangeListeners.remove(listener);
    }

    // Phương thức mặc định để thông báo cho tất cả các lắng nghe sự kiện biết rằng dữ liệu đã thay đổi
    default void notifyDataChangeListeners() {
        for (DataChangeListener listener : dataChangeListeners) {
            listener.onDataChange();
        }
    }

}
