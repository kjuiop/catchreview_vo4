package com.catchreview.qnaBoard.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.catchreview.qnaBoard.domain.QnaBoard;
import com.catchreview.qnaBoard.domain.QnaReply;


public interface QnaReplyRepository extends JpaRepository<QnaReply, Long>{

	@Query("SELECT r FROM QnaReply r WHERE r.board = ?1 "
			+ "AND r.rno > 0 ORDER BY r.rno ASC")
	public List<QnaReply> getRepliesOfBoard(QnaBoard board);
	
}