package com.ginkgocap.ywxt.video.dto.netease;


import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

/**
* @author cinderella
* @version 2017/12/20
*/
public class InteractionMemberDTO implements Serializable{

    private static final long serialVersionUID = 8348147598164128136L;

    private Long meetingId;

    private InteractionMember interactionMember;

    public InteractionMemberDTO() {
    }

    public Long getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(Long meetingId) {
        this.meetingId = meetingId;
    }

    public InteractionMember getInteractionMember() {
        return interactionMember;
    }

    public void setInteractionMember(InteractionMember interactionMember) {
        this.interactionMember = interactionMember;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
