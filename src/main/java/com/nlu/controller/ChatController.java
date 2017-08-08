package com.nlu.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nlu.dao.entity.GiangVien;
import com.nlu.model.ChatDao;
import com.nlu.model.MailBoxDao;
import com.nlu.model.MemberDao;
import com.nlu.model.Message;
import com.nlu.model.OutputMessage;
import com.nlu.repository.ChatRepository;
import com.nlu.service.ChatService;
import com.nlu.service.GiangVienService;
@Controller
public class ChatController 
{
	@Autowired
	private SimpMessagingTemplate template;
	@Autowired
	ChatService chatService  ; 
	@Autowired 
	ChatRepository chatRepository ;
	@Autowired
	GiangVienService giangVienService ;
	@RequestMapping(value="/chat")
	 public String index( Model model ) {
		 model.addAttribute("model",chatRepository.findAll()) ;
		  return "chat/index";
	 }
	// magv là ma gv dc phan cong
	   @RequestMapping(value = "/thong-bao-phan-cong", method=RequestMethod.GET)
	    public @ResponseBody boolean  thongBaoPhanCong(int  magv , String msg  , HttpServletRequest request){
	    GiangVien gv =   ManagerSession.userAdmin(request);
	    if(gv==null) {
	    	return false;
	    }else{
	   // lay ra magv la truong bo mon
	    	gv = giangVienService.giengVienById(gv.getMagv());
	    int matruongbomon  = ( gv.getMabomon().getMagv()==null)?-1:gv.getMabomon().getMagv();
	    if(matruongbomon==-1) {
	    	return false  ;
	     }else{
	    	 try {
				chatService.themNoiDung(matruongbomon+"", magv+"", msg, "");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false ;
			}
		   this.template.convertAndSend("/topic/messages", new OutputMessage(matruongbomon+"", magv+"", "Server Notifile"));
	        return  true ;
	    }
	    }
	   }
	   @RequestMapping(value = "/thong-bao-hoan-thanh", method=RequestMethod.GET)
	    public @ResponseBody boolean  sendMessage( String msg  , HttpServletRequest request){
	    GiangVien gv =   ManagerSession.userAdmin(request);
	    if(gv==null) {
	    	return false;
	    }else{
	   // lay ra magv la truong bo mon
	    	gv = giangVienService.giengVienById(gv.getMagv());
	    int matruongbomon  = ( gv.getMabomon().getMagv()==null)?-1:gv.getMabomon().getMagv();
	    if(matruongbomon==-1) {
	    	return false  ;
	     }else{
	    	 try {
				chatService.themNoiDung( gv.getMagv()+"",matruongbomon+"", msg, "");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false ;
			}
		   this.template.convertAndSend("/topic/messages", new OutputMessage( gv.getMagv()+"", matruongbomon+"", "Server Notifile"));
	        return  true ;
	    }
	    }
	   }
	   
    @MessageMapping("/chat/{topic}")
    @SendTo("/topic/messages")
    public OutputMessage send(@DestinationVariable("topic") String topic,
			      Message message) throws Exception
    {
	return new OutputMessage(message.getFrom(), message.getText(), topic);
    }
    @RequestMapping(value="/themnoidung", method = RequestMethod.POST)
    public @ResponseBody boolean themnoidung(String from , String to , String boby , String url ) {
    	System.out.println(from+" "+to +" " +boby+" "+url);
    	 try {
			return  chatService.themNoiDung(from, to, boby, url);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
    }
    @RequestMapping(value="/danhsachchat", method = RequestMethod.GET)
    public @ResponseBody List<MemberDao> danhsachchat(int magv) {
    	try {
			return chatService.danhSachChat(magv);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ArrayList<MemberDao>();
		}
    }
    @RequestMapping(value="/chatpoint", method = RequestMethod.POST)
     public @ResponseBody List<ChatDao> chatPoint(@RequestParam(name="top", defaultValue ="-1") int top ,String from , String to ) {
    	 try {
			return chatService.tinNhan(from , to, top);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ArrayList<ChatDao>() ;
		}
    }
    @RequestMapping(value="/findMailBox",method= RequestMethod.POST)
    public @ResponseBody List<MailBoxDao> findMailBox(String userId) {
    	 try {
			return  chatService.findMailBox(userId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		return  new ArrayList<MailBoxDao>();
		}
    }
     
}
