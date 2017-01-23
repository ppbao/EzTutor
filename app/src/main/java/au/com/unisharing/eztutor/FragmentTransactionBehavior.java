package au.com.unisharing.eztutor;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Class Name   :
 * Author       :
 * Created Date :
 * Description  :
 */
public class FragmentTransactionBehavior implements Parcelable {

    private final boolean popBackStack;
    private final boolean requestDisallowTOAddBackStack;
    private final String backStackName;

    protected FragmentTransactionBehavior(Parcel in){
        popBackStack = in.readByte() != 0;
        requestDisallowTOAddBackStack = in.readByte() != 0;
        backStackName = in.readString();
    }
    public FragmentTransactionBehavior(){
        this(false);
    }
    public FragmentTransactionBehavior(boolean popBackStack){
        this(popBackStack,false);
    }

    public FragmentTransactionBehavior(boolean popBackStack,
                                       boolean requestDisallowTOAddBackStack){

        this(popBackStack,requestDisallowTOAddBackStack,null);
    }
    public FragmentTransactionBehavior(boolean popBackStack,
                                       boolean requestDisallowTOAddBackStack,
                                       String backStackName){

        this.popBackStack = popBackStack;
        this.requestDisallowTOAddBackStack = requestDisallowTOAddBackStack;
        this.backStackName = backStackName;
    }

    public boolean isPopBackStack(){
        return  popBackStack;
    }
    public boolean isRequestDisallowTOAddBackStack(){
        return requestDisallowTOAddBackStack;
    }

    public String getBackStackName (){
        return  backStackName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flag) {
        parcel.writeByte((byte)(popBackStack? 1: 0));
        parcel.writeByte((byte)(requestDisallowTOAddBackStack ? 1 : 0));
        parcel.writeString(backStackName);

    }

    public static final Creator<FragmentTransactionBehavior> CREATOR = new
            Creator<FragmentTransactionBehavior>() {
                @Override
                public FragmentTransactionBehavior createFromParcel(Parcel parcel) {
                    return new FragmentTransactionBehavior(parcel);
                }

                @Override
                public FragmentTransactionBehavior[] newArray(int size) {
                    return new FragmentTransactionBehavior[size];
                }
            };
}
