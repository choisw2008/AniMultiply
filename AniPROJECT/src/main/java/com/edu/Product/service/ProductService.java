package com.edu.Product.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.edu.Product.domain.ProductDTO;
import com.edu.Product.mapper.ProductMapper;
import com.edu.board.domain.FileDTO;
import com.edu.board.domain.Pagination;

@Repository("com.edu.Product.service.ProductService")
public class ProductService {
	
	@Resource(name="com.edu.Product.mapper.ProductMapper")
	ProductMapper productMapper;
	
	public int getProductListCnt() throws Exception{
		
		return productMapper.getproductListCnt();
	}
	//게시글 목록 보기
		public List<ProductDTO> productListService(Pagination pagination) throws Exception {
			return productMapper.productList(pagination);
		}
		//게시글 등록
	public int productInsertService(ProductDTO product) throws Exception {
		return productMapper.productInsert(product);
	}
	//파일 올리기
	public int fileInsertService(FileDTO file) throws Exception {
		return productMapper.fileInsert(file);
	}
	
	public FileDTO fileDetailService(int Productno) throws Exception {
		return productMapper.fileDetail(Productno);
	}
	
//	// 서비스는 매퍼를 호출한다.
//	@Resource(name = "com.edu.Product.mapper.ProductMapper")
//	ProductMapper ProductMapper;
}
