package lopez.s.finderfeast.ui.nearme;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NearMeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public NearMeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Near Me fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}