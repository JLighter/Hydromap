package exam.hydromap.julienheroguelle.hydromap.Networking.Models.AWhereModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by julienheroguelle on 18/07/2017.
 */

public class Token {
        @SerializedName("access_token")
        @Expose
        public String accessToken;
        @SerializedName("expires_in")
        @Expose
        public Integer expiresIn;

        public long expiresAt;
}