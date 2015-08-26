package all_action.iblaudas.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import all_action.iblaudas.JsonModel.Message;
import all_action.iblaudas.R;

public class ChatListAdapter extends ArrayAdapter<Message> {
    private String mUserId ;
    private List<Message> messages;

    public ChatListAdapter(Context context, String userId, List<Message> messages) {
        super(context, 0, messages);
        this.mUserId = userId;
       this.messages = messages;

    }
    @Override
    public int getCount() {
        return messages.size();
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).
                    inflate(R.layout.chat_item, parent, false);
            final ViewHolder holder = new ViewHolder();
            holder.body = (TextView)convertView.findViewById(R.id.tvBody);
            holder.SessionChatTime = (TextView)convertView.findViewById(R.id.session_chat_time);

            convertView.setTag(holder);
        }

        final Message message = (Message)getItem(position);
        final ViewHolder holder = (ViewHolder)convertView.getTag();

        final boolean isMe = message.getSenderId().equals(mUserId);
        // Show-hide image based on the logged-in user. 
        // Display the profile image to the right for our user, left for other users.
        if (isMe) {
            RelativeLayout.LayoutParams lp =
                    new RelativeLayout.LayoutParams
                            (
                                    RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT
                            );
            lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            RelativeLayout.LayoutParams seion =
                    new RelativeLayout.LayoutParams
                            (
                                    RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT
                            );
            lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            seion.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            lp.setMargins(50, 25, 10, 0);
            seion.setMargins(0,0,10,10);
           // seion.set
            holder.body.setLayoutParams(lp);
            holder.body.setBackgroundResource(R.drawable.bg_msg_you);
            holder.body.setTextColor(Color.parseColor("#ffffff"));
            holder.SessionChatTime.setText(message.getCreated_dt());
            holder.SessionChatTime.setLayoutParams(seion);
        } else {
            RelativeLayout.LayoutParams lp =
                    new RelativeLayout.LayoutParams
                            (
                                    RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT
                            );

            RelativeLayout.LayoutParams seion =
                    new RelativeLayout.LayoutParams
                            (
                                    RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT
                            );

            lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            seion.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            lp.setMargins(10, 25, 25, 0);
            seion.setMargins(10,0,0,10);
            holder.body.setLayoutParams(lp);
            holder.body.setBackgroundResource(R.drawable.bg_msg_from);
            holder.body.setTextColor(Color.parseColor("#0C090A"));
            holder.SessionChatTime.setText(message.getCreated_dt());
            holder.SessionChatTime.setLayoutParams(seion);
        }

        holder.body.setText(message.getText());
        return convertView;
    }

    final class ViewHolder {
        public TextView body;
        public TextView SessionChatTime;
    }

}