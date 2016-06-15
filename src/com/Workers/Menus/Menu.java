package com.Workers.Menus;

import java.util.HashMap;

import com.Workers.Objects.CallBack;

public class Menu {
	
	private String massage;
	private HashMap<String, CallBack> actions;
	private boolean isOptionMenu;
	
 	public Menu(){
		massage = "Empty Menu";
		actions = new HashMap<>();
		isOptionMenu = false;
		addAction("def", new CallBack() {
			public boolean call(MenuManager manager) {
				manager.displayText("Error");
				manager.closeMenus();
				return true;
			}
		});
	}
	
	public Menu(Boolean isOption, String massage){
		this.massage = massage;
		isOptionMenu = isOption;
		actions = new HashMap<>();
		addAction("def", new CallBack() {
			public boolean call(MenuManager manager) {
				manager.displayText("Error");
				manager.closeMenus();
				return true;
			}
		});
	}
	
	public void addAction(String input, CallBack action){
		if(actions.containsKey(input))
			actions.remove(input);
		
		actions.put(input, action);
	}
	
	public String GetMassage(){
		return massage;
	}
	
	public CallBack calcInput(String input){
		if(actions.containsKey(input))
			return actions.get(input);
		else{
			return new CallBack() {
			public boolean call(MenuManager manager) {
				manager.displayText("Illigal input!\n");
				return false;
			}};
		}
	}
	
	public boolean isOption(){
		return isOptionMenu;
	}
}
