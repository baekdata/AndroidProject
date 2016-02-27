package com.masterpiecesoft.tenkw.store;

import java.util.ArrayList;
import java.util.List;

public final class UserInfo {
	
	private static String Name="�̰��";
	private static String Height;
	private static String Weight;
	private static List<Team> teamList;
	

	public static String getName() {
		return Name;
	}

	public static void setName(String name) {
		Name = name;
	}
	
	public static List<Team> getTeamList() {
		return teamList;
	}
	
	public UserInfo (){
		if(teamList==null){
			teamList = new ArrayList<Team>();
			teamList.add(new Team("sGenClub"));
			teamList.add(new Team("10KW"));
			
			teamList.get(0).getUserList().add(new TeamUserInfo("�ȼ���",3));
			teamList.get(0).getUserList().add(new TeamUserInfo("���¿�",2));
			teamList.get(0).getUserList().add(new TeamUserInfo("�ڵ���",2));
			
			teamList.get(1).getUserList().add(new TeamUserInfo("��ƹ���",4));
			teamList.get(1).getUserList().add(new TeamUserInfo("ȫ�浿",2));
			teamList.get(1).getUserList().add(new TeamUserInfo("�־�",1));
			teamList.get(1).getUserList().add(new TeamUserInfo("�ھ�",0));
		}
	}
	
	public static String getHeight() {
		return Height;
	}
	public static void setHeight(String height) {
		Height = height;
	}
	public static String getWeight() {
		return Weight;
	}
	public static void setWeight(String weight) {
		Weight = weight;
	}
	
	public static int getUserTotalStep(){
		int temp = 0;
		for(int i = 0 ; i < teamList.size() ; i++)
			temp += teamList.get(i).getUserList().get(0).getSubStep();
		return temp;
	}
	
}
