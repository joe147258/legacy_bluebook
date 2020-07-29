package jp487bluebook.app.utilities;

import java.util.Comparator;

class TempUserSimComparer implements Comparator<TempUser> {
    public int compare(TempUser tempUser1, TempUser tempUser2) {
        return tempUser1.similarity - tempUser2.similarity;
    }
}
