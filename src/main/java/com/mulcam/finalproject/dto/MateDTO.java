package com.mulcam.finalproject.dto;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.mulcam.finalproject.entity.MateImg;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MateDTO {
	private Long mid;
	private UserDTO user;
	private Long uid;
	private int state;
	private String stateName;
	private int category;
	private String categoryName;
	private String bank;
	private String accountNumber;
	private int telType;
	private String telName;
	private String telUrl;
	private String title;
	private String content;
	private String contentUpdate;
	private int price1;
	private int price2;
	private int positionNum;
	private int positonApplyNum;
	private String openChat;
	private int tradeType;
	private String tradeName;
	private String placeName;
	private String placeCoords;
	private int placeCode;
	private String placeAddr;
	private int parcelType;
	private String parcelName;
	private int parcelPrice;
	private LocalDateTime modDate;
	private int isDel;
	private int viewCnt;
	private int replyCnt;
	private int likeCnt;
	private List<MultipartFile> reqimgs;
	private List<MateImg> imgInfo; // View 전송용
	private MateImg thum;

	public void setCategory(int category) {
		String[] categoryNames = { "조각 메이트", "OTT 메이트" };
		this.category = category;
		categoryName = categoryNames[category];
	}

	public void setTradeType(int tradeType) {
		String[] tradeNames = { null, "만남거래", "택배거래", "만남거래 / 택배거래 모두 가능" };
		this.tradeType = tradeType;
		this.tradeName = tradeNames[tradeType];
	}

	public void setParcelType(int parcelType) {
		String[] parcelNames = { null, "선불", "착불" };
		this.parcelType = parcelType;
		this.parcelName = parcelNames[parcelType];
	}

	public void setTelType(int telType) {
		String[] telNames = { null, "오픈채팅", "댓글" };
		this.telType = telType;
		this.telName = telNames[telType];
	}

	public void setUser(UserDTO user) {
		this.user = user;
		this.uid = user.getUid();
	}

	public void setImgInfo(List<MateImg> imgInfo) {
		this.imgInfo = imgInfo;
		if (imgInfo.size() > 0)
			this.thum = imgInfo.get(0);
	}
	
	public void setState(int state) {
		String[] stateNames = {"N", "Y"};
		this.state = state;
		this.stateName = stateNames[state];
	}
	public void setContent(String content) {
		this.content = content;
		this.contentUpdate = content.replace("<p>","").replace("</p>", "");
	}
}
