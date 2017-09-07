package HKSM;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.AbstractButton;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class SaveEditor extends JFrame {
	
	public JsonObject json;
	public File fObject;
	
	//CHARM_NAME_23_BRK   Fragile Heart (Repair)
	//CHARM_NAME_24_BRK   Fragile Greed (Repair)
	//CHARM_NAME_25_BRK   Fragile Strength (Repair)
	
	//CHARM_NAME_36_A White Fragment
	//CHARM_NAME_36_B Kingsoul
	//CHARM_NAME_36_C Void Heart
	
	public static void validateCharms(JsonObject playerData){
		JsonArray charms = new JsonArray();
		for( int i = 1; i <= 36; i++){
			if( playerData.get("equippedCharm_"+Integer.toString(i)).getAsBoolean() )
				charms.add(i);
		}
		playerData.add("equppedCharms", charms);
	}
	
	public static String[] charmNames = new String[]{
			"Gathering Swarm",
			"Wayward Compass",
			"Grubsong",
			"Stalwart Shell",
			"Baldur Shell",
			"Fury of the Fallen",
			"Quick Focus",
			"Lifeblood Heart",
			"Lifeblood Core",
			"Defender's Crest",
			"Flukenest",
			"Thorns of Agony",
			"Mark of Pride",
			"Steady Body",
			"Heavy Blow",
			"Sharp Shadow",
			"Spore Shroom",
			"Longnail",
			"Shaman Stone",
			"Soul Catcher",
			"Soul Eater",
			"Glowing Womb",
			"Fragile Heart",
			"Fragile Greed",
			"Fragile Strength",
			"Nailmaster's Glory",
			"Joni's Blessing",
			"Shape of Unn",
			"Hiveblood",
			"Dream Wielder",
			"Dashmaster",
			"Quick Slash",
			"Spell Twister",
			"Deep Focus",
			"Grubberfly's Elegy",
			"SOON TM"
	};
	
	public SaveEditor(String path, String title){
		
		fObject = new File(path);
		SaveLoader sl = new SaveLoader();
		
		try {
			json = sl.loadSave(fObject);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JsonObject playerData = json.getAsJsonObject("playerData");
		
		this.setTitle(title);
		this.setLayout(new BorderLayout());
		
		JTabbedPane tab = new JTabbedPane();
		JMenuBar menu = new JMenuBar();
		
		JMenu file = new JMenu("File");
			JMenuItem fileSave = new JMenuItem("Save");
			
		menu.add(file);
			file.add(fileSave);
			
			
		fileSave.addActionListener( new ActionListener(){
			public void actionPerformed(ActionEvent e){
				sl.saveSave(fObject, json);
			}
		});
			
		JPanel inventoryEditor = new JPanel();
		JScrollPane inventoryScroll = new JScrollPane();
		tab.addTab("Inventory", inventoryEditor);
		
		inventoryEditor.setLayout(new GridLayout(0,3));
		
		inventoryEditor.add(new SaveField(json, new String[]{"playerData", "maxHealthBase"}, "HP", SaveField.DataType.INTEGER));
		inventoryEditor.add(new SaveField(json, new String[]{"playerData", "MPReserveMax"}, "MP", SaveField.DataType.INTEGER));
		inventoryEditor.add(new SaveField(json, new String[]{"playerData", "nailDamage"}, "Nail Damage", SaveField.DataType.INTEGER));
		
		inventoryEditor.add(new SaveField(json, new String[]{"playerData", "geo"}, "Geo", SaveField.DataType.INTEGER));
		inventoryEditor.add(new SaveField(json, new String[]{"playerData", "dreamOrbs"}, "Dream Orbs", SaveField.DataType.INTEGER));
		inventoryEditor.add(new SaveField(json, new String[]{"playerData", "permadeathMode"}, "Steel Soul", SaveField.DataType.BOOL));
		
		inventoryEditor.add(new SaveField(json, new String[]{"playerData", "hasCyclone"}, "Cyclone", SaveField.DataType.BOOL));
		inventoryEditor.add(new SaveField(json, new String[]{"playerData", "hasDashSlash"}, "Dash Slash", SaveField.DataType.BOOL));
		inventoryEditor.add(new SaveField(json, new String[]{"playerData", "hasUpwardSlash"}, "Great Slash", SaveField.DataType.BOOL));
		
		inventoryEditor.add(new SaveField(json, new String[]{"playerData", "fireballLevel"}, "Fireball Lv.", SaveField.DataType.INTEGER));
		inventoryEditor.add(new SaveField(json, new String[]{"playerData", "screamLevel"}, "Scream Lv.", SaveField.DataType.INTEGER));
		inventoryEditor.add(new SaveField(json, new String[]{"playerData", "quakeLevel"}, "Dive Lv.", SaveField.DataType.INTEGER));
		
		inventoryEditor.add(new SaveField(json, new String[]{"playerData", "hasDash"}, "Mothwing Cloak", SaveField.DataType.BOOL));
		inventoryEditor.add(new SaveField(json, new String[]{"playerData", "hasWalljump"}, "Mantis Claw", SaveField.DataType.BOOL));
		inventoryEditor.add(new SaveField(json, new String[]{"playerData", "hasSuperDash"}, "Crystal Dash", SaveField.DataType.BOOL));
		
		inventoryEditor.add(new SaveField(json, new String[]{"playerData", "hasDoubleJump"}, "Monarch Wings", SaveField.DataType.BOOL));
		inventoryEditor.add(new SaveField(json, new String[]{"playerData", "hasDreamNail"}, "Dreamnail", SaveField.DataType.BOOL));
		inventoryEditor.add(new SaveField(json, new String[]{"playerData", "hasAcidArmour"}, "Ismas", SaveField.DataType.BOOL));
		
		//Health	| Mana 		| Nail
		//Geo		| Notches   | Steel Soul
		//---------------------------------
		//N-Art1 	| N-Art2	| N-Art3
		//Spell1	| Spell2	| Spell3
		//---------------------------------
		//Dash		| Walljump	| SuperDash
		//2xJump	| Dreamnail | Ismas
		//---------------------------------
		//Lantern	| tram pass | quill
		//City key  | sly key   | white key
		//ww key    | spa key   | kings brand
		
		JPanel charmTab = new JPanel();
		charmTab.setLayout(new BorderLayout());
		
		int usedNotches = playerData.get("charmSlotsFilled").getAsInt();
		int totalNotches = playerData.get("charmSlots").getAsInt();
		
		IntJTextField eNotch = new IntJTextField(usedNotches);
		IntJTextField mNotch = new IntJTextField(totalNotches);
		
		eNotch.getDocument().addDocumentListener(new DocumentListener() {
			  public void changedUpdate(DocumentEvent e) {
			    check();
			  }
			  public void removeUpdate(DocumentEvent e) {
			    check();
			  }
			  public void insertUpdate(DocumentEvent e) {
			    check();
			  }
			  
			  public void check(){
				  playerData.addProperty("charmSlotsFilled", eNotch.getText());
			  }
		});
		
		mNotch.getDocument().addDocumentListener(new DocumentListener() {
			  public void changedUpdate(DocumentEvent e) {
			    check();
			  }
			  public void removeUpdate(DocumentEvent e) {
			    check();
			  }
			  public void insertUpdate(DocumentEvent e) {
			    check();
			  }
			  
			  public void check(){
				  playerData.addProperty("charmSlots", mNotch.getText());
			  }
		});
		
		JCheckBox oc = new JCheckBox("Overcharmed");
		oc.addActionListener( new ActionListener() {
		      public void actionPerformed(ActionEvent actionEvent) {
		          AbstractButton abstractButton = (AbstractButton) actionEvent.getSource();
		          boolean selected = abstractButton.getModel().isSelected();
		          playerData.addProperty("overcharmed", selected);
		        }
		 });
		
		
		//playerData.overcharmed
		
		JPanel charmTop = new JPanel();
		charmTop.setLayout(new GridLayout(0,2));
		charmTop.add( new JLabel("Equipped Notches") );
		charmTop.add( new JLabel("Max Notches") );
		charmTop.add( eNotch );
		charmTop.add( mNotch );
		charmTop.add( new JCheckBox("Auto-Calculate") );
		charmTop.add( oc );
		
		JPanel charmBottom = new JPanel();
		charmBottom.setLayout(new GridLayout(0,2));
		
		for( int i = 0; i < 36; i++){
			JPanel charm = new JPanel();
			charm.setLayout(new BorderLayout());
			charm.add(new JLabel( charmNames[i] ), BorderLayout.PAGE_START);
			JPanel info = new JPanel();
			info.setLayout(new GridLayout(2,3));
			info.add(new JLabel("owned"));
			info.add(new JLabel("equipped"));
			info.add(new JLabel("cost"));
						
			String s = Integer.toString(i+1);
			
			boolean ow = playerData.get("gotCharm_"+s).getAsBoolean();
			boolean eq = playerData.get("equippedCharm_"+s).getAsBoolean();
			int co = playerData.get("charmCost_"+s).getAsInt();
			
			JButton owned = new JButton(ow ? "OWN" : "!OWN");
			JButton equip = new JButton(eq ? "ON" : "OFF");
			IntJTextField cost = new IntJTextField(co);
			
			owned.addActionListener( new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent arg0) {
					boolean bool = playerData.get("gotCharm_"+s).getAsBoolean();
					playerData.addProperty("gotCharm_"+s, !bool);
					owned.setText(!bool ? "OWN" : "UNOWNED");
				}
			});
			
			equip.addActionListener( new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent arg0) {
					boolean bool = playerData.get("equippedCharm_"+s).getAsBoolean();
					playerData.addProperty("equippedCharm_"+s, !bool);
					equip.setText(!bool ? "ON" : "OFF");
				}
			});
			
			cost.getDocument().addDocumentListener(new DocumentListener() {
				  public void changedUpdate(DocumentEvent e) {
				    check();
				  }
				  public void removeUpdate(DocumentEvent e) {
				    check();
				  }
				  public void insertUpdate(DocumentEvent e) {
				    check();
				  }
				  
				  public void check(){
					  playerData.addProperty("charmCost_"+s, cost.getText());
				  }
			});
			
			info.add(owned);
			info.add(equip);
			info.add(cost);
			
			charm.add(info, BorderLayout.CENTER);
			charm.add(Box.createRigidArea(new Dimension(0,15)), BorderLayout.PAGE_END);
			charmBottom.add( charm );
			
		}
		
		charmTab.add(charmTop, BorderLayout.PAGE_START);
		charmTab.add(charmBottom, BorderLayout.CENTER);
		JScrollPane charmEditor = new JScrollPane(charmTab);
		tab.addTab("Charms", charmEditor);
		
		this.add(menu, BorderLayout.PAGE_START);
		this.add(tab, BorderLayout.CENTER);
		
		this.pack();
		
		this.setSize(450, 400);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
}