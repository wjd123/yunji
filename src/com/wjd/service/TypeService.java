package com.wjd.service;

import java.util.List;

import com.wjd.dao.TypeDao;
import com.wjd.po.NoteType;
import com.wjd.po.vo.ResultInfo;
import com.wjd.util.StringUtil;

public class TypeService {
	private TypeDao typeDao = new TypeDao();

	public List<NoteType> findTypeListByUserId(Integer userId) {
		//
		List<NoteType> typeList = typeDao.findTypeListByUserId(userId);
		
		return typeList;
	}

	public ResultInfo<NoteType> deleteType(String typeId) {
		//

		ResultInfo<NoteType> info = new ResultInfo<NoteType>();
		
		if(StringUtil.isEmpty(typeId)) {
			info.setCode(0);
			info.setMsg("网络错误，请重试！");
			return info;
		}
		
		long noteCount = typeDao.findNoteCountByTypeId(typeId);
		
		if(noteCount > 0 ) {
			info.setCode(0);
			info.setMsg("该类型存在云记记录，不可删除！");
			return info;
		}
		
		int row = typeDao.deleteTypeById(typeId);
		
		if(row>0) {
			info.setCode(1);
		}else {
			info.setCode(0);
			info.setMsg("删除失败！");
		}
		
		return info;
	}

	public ResultInfo<Integer> addOrUpdate(String typeId, String typeName, Integer userId) {
		// 
		ResultInfo<Integer> resultInfo = new ResultInfo<Integer>();
		
		if(StringUtil.isEmpty(typeName)) {
			resultInfo.setCode(0);
			resultInfo.setMsg("类型名称不可为空！");
			return resultInfo;
		}
		
		Integer code = typeDao.checkTypeName(userId,typeName,typeId);
		
		if(code == 0) {
			resultInfo.setCode(0);
			resultInfo.setMsg("类型名已存在！");
			return resultInfo;
		}
		
		// 返回的结果
		Integer key = null;
		
		if (StringUtil.isEmpty(typeId)) {
			// 如果为空，调用添加方法，返回主键
			key = typeDao.addType(typeName,userId);
		} else {
			// 如果不为空，调用修改方法，返回受影响的行数
			key = typeDao.updateType(typeName,typeId);
		}
		
		// 4、判断 主键/受影响的行数 是否大于0
		
		if(key > 0) {
			resultInfo.setCode(1);
			resultInfo.setResult(key);
		}else {
			resultInfo.setCode(0);
			resultInfo.setMsg("更新失败!");
		}
		
		
		return resultInfo;
	}
}
