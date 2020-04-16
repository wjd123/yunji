package com.wjd.service;

import java.util.List;

import com.wjd.dao.NoteDao;
import com.wjd.po.Note;
import com.wjd.po.vo.NoteVo;
import com.wjd.po.vo.ResultInfo;
import com.wjd.util.Page;
import com.wjd.util.StringUtil;

public class NoteService {
	private NoteDao noteDao = new NoteDao();

	public ResultInfo<Note> addOrUpdate(String typeId, String title, String content, String noteId) {
		// 返回ResultInfo<Note>
		ResultInfo<Note> resultInfo = new ResultInfo<Note>();

		// 1、非空判断
		if (StringUtil.isEmpty(typeId)) {
			resultInfo.setCode(0);
			resultInfo.setMsg("请选择云记类型！");
			return resultInfo;
		}
		if (StringUtil.isEmpty(title)) {
			resultInfo.setCode(0);
			resultInfo.setMsg("云记标题不可为空！");
			return resultInfo;
		}
		if (StringUtil.isEmpty(content)) {
			resultInfo.setCode(0);
			resultInfo.setMsg("云记内容不可为空！");
			return resultInfo;
		}

		Note note = new Note();
		note.setContent(content);
		note.setTitle(title);
		note.setTypeId(Integer.parseInt(typeId));
		
		// 判断不为空，则设置属性
		if (!StringUtil.isEmpty(noteId)) {
			// 将云记ID设置到note对象中
			note.setNoteId(Integer.parseInt(noteId));
		}
		
		resultInfo.setResult(note); // 设置回显对象
		
		

		int row = noteDao.addOrUpdate(note);

		if (row > 0) {
			resultInfo.setCode(1);
		} else {
			resultInfo.setCode(0);
			resultInfo.setMsg("更新失败！");
		}

		return resultInfo;
	}

	public Page<Note> findNoteListBypage(String pageNumStr, String pageSizeStr, Integer userId, String title, String date, String typeId) {
		//
		// 设置分页参数的默认值
		Integer pageNum = 1; // 默认第一页
		Integer pageSize = 10; // 默认每页显示10条数据
		// 1、判断参数是否为空，如果为空则设置默认值
		if (!StringUtil.isEmpty(pageNumStr)) {
			// 如果参数不为空，则使用前台传递的参数
			pageNum = Integer.parseInt(pageNumStr);
		}
		
		if (!StringUtil.isEmpty(pageSizeStr)) {
			// 如果参数不为空，则使用前台传递的参数
			pageSize = Integer.parseInt(pageSizeStr);
		}
		
		//查询总记录数
		long count = noteDao.findNoteCount(userId,title,date,typeId);

		//判断总数是否大于1
		if(count < 1) {
			return null;
		}
		
		//根据以上内容得到Page对象
		Page<Note> page = new Page<Note>(pageNum,pageSize,count);
		
		//得到数据库中开始的下标
		Integer index = (pageNum - 1) * pageSize;
		
		//查！
		List<Note> noteList = noteDao.findNoteByPage(userId,index,pageSize,title,date,typeId);
		
		page.setDatas(noteList);
		
		
		return page;
	}

	public Note findNoteById(String noteId) {
		// 
		// 1、参数的非空判断
		if (StringUtil.isEmpty(noteId)) {
			return null;
		}
		
		Note note = noteDao.findNoteById(noteId);
		
		
		
		return note;
	}

	public int deleteNote(String noteId) {
		// 
		if(StringUtil.isEmpty(noteId)) {
			return 0;
		}
		int row = noteDao.deleteNote(noteId);
		
		if(row>0) {
			return 1;
		}
		return 0;
	}

	public List<NoteVo> findNoteCountByDate(Integer userId) {
		// 

		List<NoteVo> noteList = noteDao.findNoteCountByDate(userId);
		return noteList;
	}

	public List<NoteVo> findNoteCountByType(Integer userId) {
		//

		List<NoteVo> noteList = noteDao.findNoteCountByType(userId);
		return noteList;
	}
}
