package com.wjd.dao;

import java.util.ArrayList;
import java.util.List;

import com.wjd.po.Note;
import com.wjd.po.vo.NoteVo;
import com.wjd.util.StringUtil;

public class NoteDao {

	public int addOrUpdate(Note note) {
		// 
		String sql = ""; 
		List<Object> params = new ArrayList<Object>();
		params.add(note.getTypeId());
		params.add(note.getTitle());
		params.add(note.getContent());
		
		if (note.getNoteId() == null) { // 添加操作
			sql = "insert into tb_note (typeId,title,content,pubTime) values (?,?,?,now())";
		 	
		} else { // 修改操作
			sql = "update tb_note set typeId = ?, title = ?, content = ?,pubTime = now() where noteId = ?";
			params.add(note.getNoteId()); 
		}
		
	

		int row = BaseDao.executeUpdate(sql, params);
		
		return row;
	}

	public long findNoteCount(Integer userId, String title, String date, String typeId) {
		// 
		String sql = "select count(1) from tb_note n INNER JOIN tb_note_type t on n.typeId = t.typeId where userId = ?";
		List<Object> params = new ArrayList<Object>();
		params.add(userId);
		
		// 判断标题是否为空
		if (!StringUtil.isEmpty(title)) {
			
			sql += " and title like concat('%',?,'%') ";
			params.add(title);
			
		} else if (!StringUtil.isEmpty(date)) { // 日期查询
			
			sql += " and date_format(pubTime,'%Y年%m月') = ? ";
			params.add(date);
		} else if (!StringUtil.isEmpty(typeId)) { // 类型查询
			
			sql += " and n.typeId = ? ";
			params.add(typeId);
		}
		
		
		
		long count = (long)BaseDao.findSingValue(sql, params);
		
		return count;
	}

	public List<Note> findNoteByPage(Integer userId, Integer index, Integer pageSize, String title, String date, String typeId) {
		// 
		String sql = "select noteId,title,pubTime from tb_note n INNER JOIN tb_note_type t on n.typeId = t.typeId where userId = ?";
		List<Object> params = new ArrayList<Object>();
		params.add(userId);
		
		// 判断标题是否为空
		if (!StringUtil.isEmpty(title)) {
			
			sql += " and title like concat('%',?,'%') ";
			params.add(title);
			
		} else if (!StringUtil.isEmpty(date)) { // 日期查询
			
			sql += " and date_format(pubTime,'%Y年%m月') = ? ";
			params.add(date);
		} else if (!StringUtil.isEmpty(typeId)) { // 类型查询
			
			sql += " and n.typeId = ? ";
			params.add(typeId);
		}
		
		sql += " order by pubTime desc limit ?,?";
		params.add(index);
		params.add(pageSize);


		
		List<Note> noteList = BaseDao.queryRows(sql, params, Note.class);
		
		return noteList;
		
	}

	public Note findNoteById(String noteId) {
		// 
		String sql = "select noteId,title,content,pubTime,t.typeId,typeName from tb_note n inner join tb_note_type t on n.typeId=t.typeId where n.noteId=?";
		List<Object> params = new ArrayList<Object>();
		params.add(noteId);
		
		Note note = (Note)BaseDao.queryRow(sql, params, Note.class);
		
		
		return note;
	}

	public int deleteNote(String noteId) {
		//
		String sql = "delete from tb_note where noteId=?";
		List<Object> params = new ArrayList<Object>();
		params.add(noteId);
		
		int row = BaseDao.executeUpdate(sql, params);
		
		
		return row;
	}

	//日期分组
	public List<NoteVo> findNoteCountByDate(Integer userId) {
		// TODO 

		String sql = "select DATE_FORMAT(pubTime,'%Y年%m月')  groupName,count(1) noteCount from tb_note n inner join tb_note_type t on n.typeId = t.typeId where userId=? GROUP BY DATE_FORMAT(pubTime,'%Y年%m月') ORDER BY DATE_FORMAT(pubTime,'%Y年%m月')  DESC";
		List<Object> params = new ArrayList<Object>();
		params.add(userId);
		
		List<NoteVo> list = BaseDao.queryRows(sql, params, NoteVo.class);
		
		return list;
	}

	//类别分组
	public List<NoteVo> findNoteCountByType(Integer userId) {
		// 
		String sql = "select typeName groupName,count(noteId) noteCount,t.typeId from tb_note n right join tb_note_type t on n.typeId = t.typeId where userId=? GROUP BY t.typeId ORDER BY count(noteId) desc";
		List<Object> params = new ArrayList<Object>();
		params.add(userId);
		
		List<NoteVo> list = BaseDao.queryRows(sql, params, NoteVo.class);
		
		return list;
		
	}

}
