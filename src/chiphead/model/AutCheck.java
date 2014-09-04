package chiphead.model;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class AutCheck {	
	//判断用户名密码是否匹配
	public static boolean YstPwdCorrect(Integer empYstId,String empPwd,SqlSession session){
		Person p = new Person();
		p.setEmp_yst_id(empYstId);
		try {
			p.setEmp_pwd(URLDecoder.decode(empPwd, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		int num = session.selectOne("chiphead.mapper.PersonMapper.selectPersonNumByEmpYstIdPwd", p);
		if(num == 1)
			return true;
		else
			return false;
	}
	
	//判断某人对于某项目是否有修改权限
	public static boolean HaveChangeProjAut(String projNo,String name,SqlSession session){
		Project proj = session.selectOne("ProjectMapper.selectProjectByProjNo", projNo);
		if (proj == null) {
			return false;
		}
		String projManager = proj.getProjManager();
		if(name.equals(projManager))
			return true;
		else{
			Person per = session.selectOne("chiphead.mapper.PersonMapper.selectPersonByEmpName",name);
			if(per == null)
				return false;
			else if(per.getEmp_aut() == 1 || per.getEmp_aut() == 3)//管理员/超级管理员
				return true;
			else
				return false;
		}
	}
	
	//判断某人对于某项目是否有删除权限
	public static boolean HaveDeleteProjAut(String projNo,String name,SqlSession session){
		Project proj = session.selectOne("ProjectMapper.selectProjectByProjNo", projNo);
		String projManager = proj.getProjManager();
		if(name.equals(projManager))
			return true;
		else{
			Person per = session.selectOne("chiphead.mapper.PersonMapper.selectPersonByEmpName",name);
			if(per == null)
				return false;
			else if(per.getEmp_aut() == 1 || per.getEmp_aut() == 3)//管理员/超级管理员
				return true;
			else
				return false;
		}
	}
	
	
	//判断某人对于某项目是否有归档权限
	public static boolean HaveArchiveProjAut(String projNo,String name,SqlSession session){
		Project proj = session.selectOne("ProjectMapper.selectProjectByProjNo", projNo);
		String projManager = proj.getProjManager();
		if(name.equals(projManager))
			return true;
		else{
			Person per = session.selectOne("chiphead.mapper.PersonMapper.selectPersonByEmpName",name);
			if(per == null)
				return false;
			else if(per.getEmp_aut() == 1 || per.getEmp_aut() == 3)//管理员/超级管理员
				return true;
			else
				return false;
		}
	}
	
	
	//判断某人对另一人的排期是否有审核权限
	public static boolean HaveCheckStateAut(Integer empYstId,String empName,SqlSession session){
		//根据一事通id查询审核人的记录
		Person per = session.selectOne("chiphead.mapper.PersonMapper.selectPersonByEmpYstId", empYstId);
		//根据姓名查询被审核人的记录
		Person per2 = session.selectOne("chiphead.mapper.PersonMapper.selectPersonByEmpName",empName);
		if(per == null || per2 == null)
			return false;
		else if(per.getEmp_aut() == 2)
			//如果审核人是普通用户
			return false;
		else if(per.getEmp_aut() == 1){//审核人是管理员，小组长
			if(per2.getEmp_aut() == 2 && per.getTeam_id() == per2.getTeam_id())
			//审核人和被审核人同一组,被审核人是普通用户
				return true;
			else
				return false;
		}
		else if(per.getEmp_aut() == 3){//审核人是超级管理员
			if(per2.getEmp_aut() == 2 || per2.getEmp_aut() == 1)
				//被审核人是普通用户或管理员
				return true;
			else
				return false;
		}
		else
			return false;
	}
	
	
	//判断某人对项目后评估是否有审核权限
	public static boolean HaveCheckEvaluateProjAut(Integer empYstId,SqlSession session){
		//根据一事通id查询
		Person per = session.selectOne("chiphead.mapper.PersonMapper.selectPersonByEmpYstId", empYstId);
		if(per == null)
			return false;
		if(per.getEmp_aut() == 3){//审核人是超级管理员
			return true;
		}
		else
			return false;
	}
	
	
	//判断某人对于某运维条目是否有修改权限
	public static boolean HaveChangeOperAut(Integer operNo,Integer empYstId,SqlSession session){
		//根据一事通id从persons取数据
		Person per = session.selectOne("chiphead.mapper.PersonMapper.selectPersonByEmpYstId", empYstId);
		//根据编号在operations中查询一条
		Operation op = session.selectOne("OperationMapper.selectOperationByOperNo", operNo);
		if (per == null || op == null) {
			return false;
		}
		//运维人员，或普通管理员或超级管理员，或马海波或侯智勇有修改权限
		if(op.getOperators().contains(per.getEmp_name()) 
				|| per.getEmp_aut() == 1 || per.getEmp_aut() == 3 
				|| per.getEmp_name().equals("马海波") || per.getEmp_name().equals("侯智勇"))
			return true;
		else
			return false;
	}
	
	//判断某人对于某运维条目是否有删除权限
	public static boolean HaveDeleteOperAut(Integer operNo,Integer empYstId,SqlSession session){
		//根据一事通id从persons取数据
		Person per = session.selectOne("chiphead.mapper.PersonMapper.selectPersonByEmpYstId", empYstId);
		//根据编号在operations中查询一条
		Operation op = session.selectOne("OperationMapper.selectOperationByOperNo", operNo);
		if (per == null || op == null) {
			return false;
		}
		//运维人员，或普通管理员或超级管理员，或马海波或侯智勇有删除权限
		if(op.getOperators().contains(per.getEmp_name()) 
				|| per.getEmp_aut() == 1 || per.getEmp_aut() == 3 
				|| per.getEmp_name().equals("马海波") || per.getEmp_name().equals("侯智勇"))
			return true;
		else
			return false;
	}
	
	
	//判断某人对运维表中记录是否有审核权限（马海波或侯智勇）
	public static boolean HaveCheckOperationAut(Integer empYstId,SqlSession session) {
		//根据一事通id查询
		Person per = session.selectOne("chiphead.mapper.PersonMapper.selectPersonByEmpYstId", empYstId);
		if(per == null)
			return false;
		if(per.getEmp_aut() == 1 || per.getEmp_aut() == 3){//审核人是普通管理员或超级管理员
//		if(per.getEmp_name().equals("马海波") || per.getEmp_name().equals("侯智勇")){
			return true;
		}
		else
			return false;
	}
	
	//判断某人对于某创新条目是否有增加权限(结论字段只能由管理员或超级管理员填写，其他字段不设权限限制)
	public static boolean HaveRegInnoAut(String conclusion,Integer empYstId,SqlSession session) {
		//根据一事通id从persons取数据
		Person per = session.selectOne("chiphead.mapper.PersonMapper.selectPersonByEmpYstId", empYstId);
		if (per == null) {
			return false;
		}
		//是管理员或者超级管理员或者结论字段为空，有权限
		if(per.getEmp_aut() == 1 || per.getEmp_aut() == 3 || conclusion.isEmpty())
			return true;
		else
			return false;
	}
	
	
	//判断某人对于某创新条目是否有修改权限(普通用户只能修改自己在提出人中的记录（除结论字段），管理员或超级管理员可修改所有记录所有字段)
	public static boolean HaveChangeInnoAut(Integer innoNo,String conclusion,Integer empYstId,SqlSession session){
		//根据一事通id从persons取数据
		Person per = session.selectOne("chiphead.mapper.PersonMapper.selectPersonByEmpYstId", empYstId);
		//根据编号在innovations中查询一条
		Innovation in = session.selectOne("InnovationMapper.selectInnovationByInnoNo", innoNo);
		if (per == null || in == null) {
			return false;
		}
		
		//普通管理员或超级管理员或者conclusion未作修改，有权限
		if(per.getEmp_aut() == 1 || per.getEmp_aut() == 3)
			return true;
		else if(per.getEmp_aut() == 2 && in.getProposers().contains(per.getEmp_name()) && conclusion.equals(in.getConclusion())){
			return true;
		}
		else
			return false;
	}
	
	//判断某人对于某创新条目是否有删除权限(普通管理员或超级管理员有删除权限)
	public static boolean HaveDeleteInnoAut(Integer innoNo,Integer empYstId,SqlSession session){
		//根据一事通id从persons取数据
		Person per = session.selectOne("chiphead.mapper.PersonMapper.selectPersonByEmpYstId", empYstId);
		//根据编号在innovations中查询一条
		Innovation in = session.selectOne("InnovationMapper.selectInnovationByInnoNo", innoNo);
		if (per == null || in == null) {
			return false;
		}
		//普通管理员或超级管理员有删除权限
		if(per.getEmp_aut() == 1 || per.getEmp_aut() == 3)
			return true;
		else
			return false;
	}
	
	public static boolean HaveDeleteEmerAut(Integer emerNo,Integer empYstId,SqlSession session){
		//根据一事通id从persons取数据
		Person per = session.selectOne("chiphead.mapper.PersonMapper.selectPersonByEmpYstId", empYstId);
		//根据编号在innovations中查询一条
		Emergency em = session.selectOne("EmergencyMapper.selectEmergencyByEmerNo", emerNo);
		if (per == null || em == null) {
			return false;
		}
		//普通管理员或超级管理员有删除权限
		if(per.getEmp_aut() == 1 || per.getEmp_aut() == 3)
			return true;
		else
			return false;
	}
	
	/*
	public static void main(String[] args) throws IOException{
		InputStream inputStream = Resources.getResourceAsStream(Constants.MYBATISCONFIG);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder()
				.build(inputStream);
		SqlSession session = sqlSessionFactory.openSession();
		String projNo = "TEST11";
		String name = "嗷嗷";
		System.out.println(HaveDeleteProjAut(projNo,name,session));
	
		int[] ymw = ComputeWeekNum.getYearMonthWeekNoByColId(5);
		int year = ymw[0];
		int month = ymw[1];
		int weekNo = ymw[2];
		System.out.println(year);
		System.out.println(month);
		System.out.println(weekNo);
	
	}
	*/
}


	
	
