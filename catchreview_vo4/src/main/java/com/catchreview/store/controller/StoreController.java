package com.catchreview.store.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.catchreview.common.awsUpload.S3UploaderService;
import com.catchreview.common.vo.PageMaker;
import com.catchreview.join.domain.Member;
import com.catchreview.point.domain.Point;
import com.catchreview.point.persistence.PointRepository;
import com.catchreview.qnaBoard.vo.PageVO;
import com.catchreview.review.domain.Review;
import com.catchreview.review.persistence.ReviewRepository;
import com.catchreview.reward.domain.Reward;
import com.catchreview.reward.persistence.RewardRepository;
import com.catchreview.store.domain.Store;
import com.catchreview.store.persistence.StoreRepository;

import lombok.extern.java.Log;

@Controller
@Log
@RequestMapping("/store/")
public class StoreController {
	
	@Autowired
	private StoreRepository storeRepository;
	
	@Autowired
	private PointRepository pointRepo;
	
	@Autowired
	private RewardRepository rewardRepo;
	
	@Autowired
	private S3UploaderService s3Uploader;
	
	@Autowired
	private ReviewRepository reviewRepo;
	
	@GetMapping("/view")
	public void view(Long storeNum, @ModelAttribute("pageVO") PageVO pageVO, Model model) {
		log.info("storeNum : " + storeNum);
		storeRepository.findById(storeNum).ifPresent(store -> model.addAttribute("vo", store));
		
		Pageable page = pageVO.makePageable(0, "reviewNum");
		Page<Review> result = reviewRepo.findAll(reviewRepo.makePredicate(storeNum), page);
		model.addAttribute("result", new PageMaker(result));
	}
	
	@GetMapping("/storeRegist")
	public String storeRegist() {
		System.out.println("Store Register---");
		return "store/storeRegist";
	}
	
	@PostMapping("/storeRegist")
	public String resgisterPost(@ModelAttribute("storeVO")Store storeVO, @ModelAttribute("rewardVO")Reward rewardVO, RedirectAttributes rttr,
			@RequestParam("id") Long id,
			@RequestParam("file1")MultipartFile file1, 
			@RequestParam("file2")MultipartFile file2, 
			@RequestParam("file3")MultipartFile file3, 
			@RequestParam("file4")MultipartFile file4) {
		log.info("register post");
		log.info("" + storeVO);
		log.info("id : " + id);
		log.info("file : " + file1);
		log.info("rewardVO : " + rewardVO);
		
		
		Member member = new Member();
		member.setId(id);
		storeVO.setUser(member);
		
		storeRepository.save(storeVO);
		
		
		Optional<Store> opStore = storeRepository.findByStoreName(storeVO.getStoreName());
		Store store = opStore.get();
		
		rewardVO.setStore(store);
		
		rewardRepo.save(rewardVO);
		
		Point pointVO = new Point();
		pointVO.setUser(member);
		pointVO.setPointAmounts(rewardVO.getRewardAmounts());
		pointVO.setIo("o");
		pointVO.setPointContent(storeVO.getStoreName() + " reward로 " + rewardVO.getRewardAmounts() + "게시");
		
		pointRepo.save(pointVO);
		
		
		
		
		s3Uploader.uploadfile(file1);
		s3Uploader.uploadfile(file2);
		s3Uploader.uploadfile(file3);
		s3Uploader.uploadfile(file4);
		rttr.addFlashAttribute("msg", "success");

		return "redirect:/mypage";
	}
}
