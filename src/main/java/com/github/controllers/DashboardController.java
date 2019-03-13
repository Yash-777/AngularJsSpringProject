package com.github.controllers;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.dao.MongoFilesDAO_Impl;


@Controller
@RequestMapping(value = "/user")
public class DashboardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	//@Autowired private LoginDAO_Impl loginDao;
	
	@Autowired
	private MongoFilesDAO_Impl mongoDao;
	
	@RequestMapping(value = "/getDashboardDetails", method = RequestMethod.POST )
	public void getDashboardDetails(HttpServletRequest request, HttpServletResponse response ) throws IOException {
		
		/*Using writer to send a message to requested resource*/
		PrintWriter writer = response.getWriter();
		System.out.println("POST...  getDashboardDetails");
		StringBuilder sb = new StringBuilder();
		BufferedReader br = request.getReader();
		String str = null;
		while ((str = br.readLine()) != null) {
			sb.append(str);
		} // SB: {"email":"yashwanth.merugu@gmail.com"}
		System.out.println("SB : "+sb);
		
		JSONObject jObj = null;
		String returnMessage = "error";
		try {
			jObj = new JSONObject(sb.toString());
			System.out.println( jObj );
			
			/*String email = null;
			email = jObj.getString("email");
			List<DashBoardDetailsDTO> dashBoard = loginDao.getAutomationDetails( email );
			if( dashBoard != null ) {
				// Cover to JSON and add to PW.
				returnMessage = list2JSON(dashBoard);
			}*/
		} catch (JSONException e) {
			e.printStackTrace();
		}
		writer.print(returnMessage);
	}
	
	@RequestMapping(value = "/imageStream", method = RequestMethod.GET )
	public void imageStream(HttpServletRequest request, HttpServletResponse response ) throws IOException {
		System.out.println("re jjfkdjsfksdflskdljf");
		String fileObjectID = request.getParameter("id");
		System.out.println("Requested File ID : "+fileObjectID);
		
		ServletOutputStream sos = response.getOutputStream();
		InputStream image = mongoDao.getImageStream( fileObjectID );
		System.out.println("image :: "+image);
		BufferedInputStream bin = new BufferedInputStream( image );
		BufferedOutputStream bout = new BufferedOutputStream( sos );
		int ch =0; ;
		while((ch=bin.read())!=-1) {
			bout.write(ch);
		}
		bin.close();
		image.close();
		bout.close();
		//sos.write(image);
		sos.close();
		
	}
	
	@RequestMapping(value = "/videoStream", method = RequestMethod.GET, produces = "video/mp4" )
	@ResponseBody
	public void videoStream(HttpServletRequest request, HttpServletResponse response ) throws IOException {
		response.setContentType("video/mp4");
		/* Getting 2 request at same time with different request TYPES.
		 * document TYPE - Size 13 B « media TYPE - Size 1.4 MB
		 */
		
		String fileID = request.getParameter("id");
		System.out.println("Requested File ID : "+fileID);
		
		ServletOutputStream sos = response.getOutputStream();
		//response.setContentType(org.springframework.http.MediaType.APPLICATION_OCTET_STREAM_VALUE);
		// Set the content type and attachment header.
		//response.addHeader("Content-disposition", "attachment;filename=myfilename.mp4");
		
		InputStream video = mongoDao.getVideoStream( fileID );
		System.out.println( video );
		BufferedInputStream bin = new BufferedInputStream( video );
		BufferedOutputStream bout = new BufferedOutputStream( sos );
		int ch =0; ;
		while((ch=bin.read())!=-1) {
			bout.write(ch);
		}
		bin.close();
		video.close();
		bout.close();
		sos.close();
	}
	
	@RequestMapping(value = "/videoStream_1", method = RequestMethod.GET )
	@ResponseBody
	public FileSystemResource videoStream2(HttpServletRequest request, HttpServletResponse response ) throws IOException {
		System.out.println("================/videoStream");
		String fileID = request.getParameter("id");
		System.out.println("Requested File ID : "+fileID);
		
		String tempDir = System.getProperty("user.dir");
		String targetFile = tempDir+"/testFile.mp4";
		System.out.println("File : "+ targetFile);
		
		InputStream initialStream = mongoDao.getVideoStream( fileID );
		//FileUtils.copyInputStreamToFile(initialStream, targetFile);
		
		byte[] buffer = new byte[initialStream.available()];
		initialStream.read(buffer);
		@SuppressWarnings("resource")
		OutputStream outStream = new FileOutputStream(targetFile);
		outStream.write(buffer);
		
		// https://stackoverflow.com/q/20634603/5081877
		return new FileSystemResource( targetFile );
	}
	
}
