package com.wjd.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.wjd.po.NoteType;
import com.wjd.util.DBUtil;

public class TypeDao {

	public List<NoteType> findTypeListByUserId(Integer userId) {
		//

		String sql = "select * from tb_note_type where userId = ?";

		List<Object> params = new ArrayList<Object>();
		params.add(userId);

		List<NoteType> list = BaseDao.queryRows(sql, params, NoteType.class);

		return list;
	}

	public long findNoteCountByTypeId(String typeId) {
		//
		String sql = "select count(1) from tb_note where typeId = ?";
		// canshu
		List<Object> params = new ArrayList<Object>();
		params.add(typeId);
		//
		long count = (long) BaseDao.findSingValue(sql, params);
		return count;
	}

	public int deleteTypeById(String typeId) {
		//

		String sql = "delete from tb_note_type where typeId = ?";

		List<Object> params = new ArrayList<Object>();
		params.add(typeId);

		int row = BaseDao.executeUpdate(sql, params);

		return row;
	}

	public Integer checkTypeName(Integer userId, String typeName, String typeId) {

		String sql = "select * from tb_note_type where userId=? and typeName=?";
		List<Object> params = new ArrayList<Object>();
		params.add(userId);
		params.add(typeName);

		NoteType type = (NoteType) BaseDao.queryRow(sql, params, NoteType.class);

		// 如果对象为空，则可用，返回1
		if (type == null) {
			return 1;
		} else {
			// 如果查询到对象，则判断是否是当前记录
			if ((type.getTypeId().toString()).equals(typeId)) {
				return 1;
			}
		}
		return 0;

	}

	public Integer addType(String typeName, Integer userId) {
		Integer key = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null; // 接收主键的结果集
		try {

			// 得到数据库连接
			connection = DBUtil.getConnection();
			// 定义sql语句
			String sql = "insert into tb_note_type (typeName,userId) values (?,?)";
			// 预编译 （返回主键）
			preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			// 设置参数
			preparedStatement.setString(1, typeName);
			preparedStatement.setInt(2, userId);
			// 执行更新，返回受影响的行数
			int row = preparedStatement.executeUpdate();
			// 判断受影响的行数
			if (row > 0) {
				// 获取返回主键的结果集
				resultSet = preparedStatement.getGeneratedKeys();
				// 得到主键的值
				while (resultSet.next()) {
					key = resultSet.getInt(1);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 关闭资源
			DBUtil.close(resultSet, preparedStatement, connection);
		}
		return key;
	}

	public Integer updateType(String typeName, String typeId) {
		//
		String sql = "update tb_note_type set typeName = ? where typeId = ? ";
		List<Object> params = new ArrayList<Object>();
		params.add(typeName);
		params.add(typeId);

		int row = BaseDao.executeUpdate(sql, params);

		return row;
	}

}
