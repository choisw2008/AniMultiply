package com.edu.Product.controller;


import java.io.File;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.edu.Product.domain.ProductDTO;
import com.edu.Product.service.ProductService;
import com.edu.board.domain.FileDTO;
import com.edu.board.domain.Pagination;

@Controller
@RequestMapping("/product")
public class ProductController {
	
	@Resource(name = "com.edu.Product.service.ProductService")
	ProductService productService;
	
	// 게시글 목록 보여주기
	@RequestMapping(value = "/productlist", method = RequestMethod.GET)
	private String boardList(Model model, @RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false, defaultValue = "1") int range) throws Exception {

		// 전체 게시글 개수
		int listCnt = productService.getProductListCnt();

		// Pagination 객체생성
		Pagination pagination = new Pagination();

		pagination.pageInfo(page, range, listCnt);
		
		model.addAttribute("pagination", pagination);
		model.addAttribute("list", productService.productListService(pagination));
		
		return "/product/productlist";
	}
	
	// 웹 브라우저에서 http://localhost:8088/Product/Productinsert 로 호출한다.
		@RequestMapping("/productinsert")
		private String boardInsertForm() {
			System.out.println("Controller insert......");
			return "/product/productinsert";
		}

		// Controller 에서 Multipart 를 @RequestParet 어노테이션을 통해 별도의 설정없이 사용할 수 있다.
		@RequestMapping("/insertProc")
		private String boardInsertProc(HttpServletRequest request, @RequestPart MultipartFile files) throws Exception {

			// 게시글 등록 화면에서 입력한 값들을 실어나르기 위해 BoardVO를 생성한다.
			ProductDTO product = new ProductDTO();
			FileDTO file = new FileDTO();
			
			product.setProductname(request.getParameter("productname"));
			product.setProductimagefile(request.getParameter("Productimagefile"));
			
			
			if (files.isEmpty()) { // 업로드할 파일이 없는 경우
				productService.productInsertService(product); // 게시글만 올린다.
			} else { // 업로드할 파일이 있는 경우
				// FilenameUtils : commons-io defendency를 사용.
				String fileName = files.getOriginalFilename();
				String fileNameExtension = FilenameUtils.getExtension(fileName).toLowerCase();
				File destinationFile;
				String destinationFileName;
				// fileUrl = "uploadFiles 폴더의 위치";
				// uploadFiles 폴더의 위치 확인 : uploadFiles 우클릭 -> Properties -> Resource - >
				// Location
				String fileUrl = "C:/workspaceSP/sboard/src/main/webapp/WEB-INF/uploadFiles/";

				do {
					destinationFileName = RandomStringUtils.randomAlphanumeric(32) + "." + fileNameExtension;
					destinationFile = new File(fileUrl + destinationFileName);
				} while (destinationFile.exists());

				// MultipartFile.transferTo() : 요청 시점의 임시 파일을 로컬 파일 시스템에 영구적으로 복사해준다.
				destinationFile.getParentFile().mkdirs();
				files.transferTo(destinationFile);

				productService.productInsertService(product); // 게시글 올리기

				// 파일관련 자료를 Files 테이블에 등록한다.
				file.setBno(product.getProductno());
				file.setFileName(destinationFileName);
				file.setFileOriName(fileName);
				file.setFileUrl(fileUrl);

				productService.fileInsertService(file);
			}

			return "redirect:/product/productlist";
		}
}
