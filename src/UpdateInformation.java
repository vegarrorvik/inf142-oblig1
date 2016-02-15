import java.net.SocketAddress;
import java.util.Date;

/**
 * Created by vegar on 2/15/16.
 */
public class UpdateInformation {
    private Date date;
    private SocketAddress ipAddress;
    private String action;

    public UpdateInformation(String action, Date date, SocketAddress ipAddress) {
        this.date = date;
        this.ipAddress = ipAddress;
        this.action = action;
    }

    public String getAction() {
        return action;
    }

    public Date getDate() {
        return date;
    }

    public SocketAddress getIpAddress(){
        return ipAddress;
    }

    @Override
    public String toString() {
        return "UpdateInformation{" +
                "action='" + action + '\'' +
                ", date=" + date +
                ", ipAddress=" + ipAddress +
                '}';
    }
}
