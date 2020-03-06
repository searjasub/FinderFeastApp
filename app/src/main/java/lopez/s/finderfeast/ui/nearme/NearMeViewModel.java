package lopez.s.finderfeast.ui.nearme;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NearMeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public NearMeViewModel() {
        mText = new MutableLiveData<>();
    }

    public LiveData<String> getText() {
        return mText;
    }
}