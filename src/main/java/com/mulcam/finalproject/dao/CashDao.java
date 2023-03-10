package com.mulcam.finalproject.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.mulcam.finalproject.entity.Cash;
import com.mulcam.finalproject.entity.CashImg;

@Mapper
public interface CashDao{

		@Select("select * from cash where cid=#{cid}")
		public Cash getCash(int cid);

		/* 수입지출 등록 DB로 데이터 보내기 */
		@Insert("INSERT INTO cash VALUES(DEFAULT, #{category}, #{regDate}, #{amount}, #{content}, #{memo}, #{uid})")
		public void cashSave(Cash cash);

		/* cid 값 가져오기 */
		@Select("SELECT cid FROM cash"
				+ " WHERE uid=#{uid} ORDER BY cid DESC LIMIT 1")
		public int getRecentCid(String uid);

		/* 영수증 이미지 DB로 보내기 */
		@Insert("INSERT INTO ocr_img VALUES(DEFAULT,#{fileName},#{ext}, #{origFileName}, #{filePath}, #{saveDate}, #{cid})")
		public void ocrImgSave(CashImg cashImg);

		/* 해당 기간별 수입&지출 리스트 */
		@Select("SELECT * FROM cash"
				+ " WHERE uid=#{uid} AND date(regDate) between #{startDate} AND #{endDate}")
		List<Cash> getList(String uid, String startDate, String endDate);

		/* 오늘 '지출'합계 구하기 */
		@Select("SELECT SUM(amount) FROM cash"
				+ " WHERE uid=#{uid} AND category=0 AND regDate=DATE(NOW())")
		public int sumNowExpense(String uid);

		/* 오늘 '수입'합계 구하기 */
		@Select("SELECT SUM(amount) FROM cash"
				+ " WHERE uid=#{uid} AND category=1 AND regDate=DATE(NOW())")
		public int sumNowIncome(String uid);


		/* 수입지출 전체리스트 가져오기 (오늘날짜부터 한달치) */
		// 한달동안 list 뽑기 -> 날짜기준 -> 목록출력
		@Select("SELECT a.cid, a.category, a.content,a.amount,a.memo,a.regDate, b.fileName, b.ext, b.saveDate"
				+ " FROM cash AS a"
				+ " LEFT JOIN ocr_img AS b"
				+ " ON a.cid = b.cid"
				+ " WHERE uid=#{uid} AND regDate BETWEEN DATE_ADD(NOW(), INTERVAL -1 MONTH ) AND NOW()"
				+ " ORDER BY a.regDate DESC")
		List<Cash> getAllCashList(String uid);

		/* 수입지출 전체리스트 날짜기준 -> 목록출력 */
		@Select("SELECT a.cid, a.category, a.content,a.amount,a.memo,a.regDate, b.fileName, b.ext, b.saveDate"
				+ " FROM cash AS a"
				+ " LEFT JOIN ocr_img AS b"
				+ " ON a.cid = b.cid"
				+ " WHERE uid=#{uid} AND regDate=#{regDate}"
				+ " ORDER BY a.regDate DESC")
		List<Cash> getCashListByDate(String uid,String regDate);
		
		@Select("UPDATE cash"
				+ "	SET"
				+ "		category=#{category},"
				+ "		regDate=#{regDate},"
				+ "		amount=#{amount},"
				+ "		content=#{content},"
				+ "		memo=#{memo}"
				+ "	WHERE cid=#{cid}")
		public void updateCash(Cash cash);
		
		@Delete("DELETE FROM cash WHERE cid=#{cid}")
		public void deleteCash(int cid);
		
}
