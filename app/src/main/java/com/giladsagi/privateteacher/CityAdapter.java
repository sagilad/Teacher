package com.giladsagi.privateteacher;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class CityAdapter extends BaseExpandableListAdapter {
	private Context context;
	private String sendActivity;
	static String [][]childList;
	static String []parentList;
	static String []regions={"צפון","אזור הכרמל והעמקים","אזור השרון והשומרון","מרכז","אזור ירושלים ויהודה","שפלה ומישור החוף הדרומי","דרום"};
	static String [][]cities={
			{"כל אזור הצפון","עכו - נהריה והסביבה","גליל עליון","הכנרת והסביבה","כרמיאל והסביבה","נצרת והסביבה","ראש פינה","רמת הגולן","גליל תחתון","קריית שמונה והסביבה"},
			{"כל אזור כרמל ועמקים","חיפה והסביבה","קריות והסביבה","זכרון וחוף הרכמל","עפולה והעמקים","עמק בית שאן","רמת מנשה","קיסריה והסביבה","בנימינה","יקנעם והסביבה"},
			{"כל אזור השרון והשומרון","חדרה והסביבה","נתניה והסביבה","יישובי עמק חפר","רמת השרון - הרצליה","רעננה - כפר סבא","הוד השרון והסביבה","אריאל והסביבה","יישובי שומרון"},
			{"כל אזור המרכז","תל אביב - יפו","רמת גן - גבעתיים","פתח תקווה והסביבה","חולון - בת ים","ראשון לציון והסביבה","בקעת אונו","ראש העין והסביבה","רמלה - לוד","גבעת שמואל - בני ברק","שוהם והסביבה","מודיעין והסביבה"},
			{"כל אזור ירושלים ויהודה","ירושלים","אזור לטרון","בית שמש והסביבה","מעלה אדומים והסביבה","מבשרת והסביבה","הרי יהודה והסביבה","גוש עציון","בקעת הירדן","דרום הר חברון"},
			{"כל אזור השפלה ומישור החוף הדרומי","נס ציונה - רחובות","אשדוד והסביבה","אשקלון והסביבה","גדרה - יבנה","קריית גת והסביבה"},
			{"כל אזור הדרום","באר שבע והסביבה","אילת והערבה","אזור ים המלח - ערד","שדרות - נתיבות","עוטף עזה","ירוחם - דימונה", "מצפה ויישובי הנגב"}
			};
	static String [][]cities1={
		{"עכו - נהריה והסביבה","גליל עליון","הכנרת והסביבה","כרמיאל והסביבה","נצרת והסביבה","ראש פינה","רמת הגולן","גליל תחתון","קריית שמונה והסביבה"},
		{"חיפה והסביבה","קריות והסביבה","זכרון וחוף הרכמל","עפולה והעמקים","עמק בית שאן","רמת מנשה","קיסריה והסביבה","בנימינה","יקנעם והסביבה"},
		{"חדרה והסביבה","נתניה והסביבה","יישובי עמק חפר","רמת השרון - הרצליה","רעננה - כפר סבא","הוד השרון והסביבה","אריאל והסביבה","יישובי שומרון"},
		{"תל אביב - יפו","רמת גן - גבעתיים","פתח תקווה והסביבה","חולון - בת ים","ראשון לציון והסביבה","בקעת אונו","ראש העין והסביבה","רמלה - לוד","גבעת שמואל - בני ברק","שוהם והסביבה","מודיעין והסביבה"},
		{"ירושלים","אזור לטרון","בית שמש והסביבה","מעלה אדומים והסביבה","מבשרת והסביבה","הרי יהודה והסביבה","גוש עציון","בקעת הירדן","דרום הר חברון"},
		{"נס ציונה - רחובות","אשדוד והסביבה","אשקלון והסביבה","גדרה - יבנה","קריית גת והסביבה"},
		{"באר שבע והסביבה","אילת והערבה","אזור ים המלח - ערד","שדרות - נתיבות","עוטף עזה","ירוחם - דימונה", "מצפה ויישובי הנגב"}
		};
	static String []categories={"ספורט","מוזיקה","מקצועות בית ספר","שפות","יישומי המחשב","אקדמיה","שונות"};
	static String [][]subjects={
			{"כל ענפי הספורט","יוגה","קונג פו","אומנויות לחימה","פילאטיס","רצועות TRX","עיצוב וחיטוב","שחייה","אימון אישי","ספורט אחר"},
			{"כל תחומי המוזיקה","גיטרה","פסנתר","תופים","כלי נשיפה","כלי קשת","שירה ופיתוח קול","מוזיקה אחר"},
			{"כל מקצועות בית הספר","מתמטיקה","פיסיקה","כימיה","ביולוגיה","היסטוריה","אזרחות","לשון","תנך","ספרות","מחשבים","מקצועות נוספים"},
			{"כל השפות","אנגלית","עברית","רוסית","ערבית","ספרדית","צרפתית","שפה אחרת"},
			{"כל תחומי המחשב","שפות תכנות","בניית אתרים","פיתוח אפליקציות","יישומי Office","עיצוב גרפי","מחשבים אחר"},
			{"כל מקצועות האקדמיה","חשבון דיפרנציאלי ואינטגרלי","לינארית","כלכלה","הנדסה כללי","הנדסת בניין","הנדסת תעשייה וניהול","הנדסת חשמל","הנדסת מערכות מידע","הנדסת תקשורת","הנדסת חומרים","הנדסת מכונות","הנדסת תוכנה","חשבונאות","חשמל","אקדמיה אחר"},
			{"אומנות","צילום","תקשורת","קולנוע","משחק","איפור","בישול","תחום אחר"}
			};
	static String [][]subjects1={
		{"יוגה","קונג פו","אומנויות לחימה","פילאטיס","רצועות TRX","עיצוב וחיטוב","שחייה","אימון אישי","ספורט אחר"},
		{"גיטרה","פסנתר","תופים","כלי נשיפה","כלי קשת","שירה ופיתוח קול","מוזיקה אחר"},
		{"מתמטיקה","פיסיקה","כימיה","ביולוגיה","היסטוריה","אזרחות","לשון","תנך","ספרות","מחשבים","מקצועות נוספים"},
		{"אנגלית","עברית","רוסית","ערבית","ספרדית","צרפתית","שפה אחרת"},
		{"שפות תכנות","בניית אתרים","פיתוח אפליקציות","יישומי Office","עיצוב גרפי","מחשבים אחר"},
		{"חשבון דיפרנציאלי ואינטגרלי","לינארית","כלכלה","הנדסה כללי","הנדסת בניין","הנדסת תעשייה וניהול","הנדסת חשמל","הנדסת מערכות מידע","הנדסת תקשורת","הנדסת חומרים","הנדסת מכונות","הנדסת תוכנה","חשבונאות","חשמל","אקדמיה אחר"},
		{"אומנות","צילום","תקשורת","קולנוע","משחק","איפור","בישול","תחום אחר"}
		};

	public CityAdapter(Context context, String sendActivity) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.sendActivity = sendActivity;
		if (sendActivity.equals("City"))
		{
			parentList =regions;
			childList = cities;
		}
		if (sendActivity.equals("Subject"))
		{
			parentList =categories;
			childList = subjects;
		}
		if (sendActivity.equals("City1"))
		{
			parentList =regions;
			childList = cities1;
		}
		if (sendActivity.equals("Subject1"))
		{
			parentList =categories;
			childList = subjects1;
		}		
		
	}

	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return parentList.length;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		return childList[groupPosition].length;
	}

	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return groupPosition;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub			
		return null;
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		TextView tv = new TextView(context);
		tv.setText(parentList[groupPosition]);				
		tv.setPadding(0, 15, 15, 15);
		tv.setTextSize(24);
		tv.setTextColor(Color.WHITE);
		return tv;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		TextView tv = new TextView(context);
		tv.setText(childList[groupPosition][childPosition]);
		tv.setPadding(0, 10, 40, 10);
		tv.setTextSize(16);
		tv.setTextColor(Color.WHITE);
		return tv;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return true;
	}

}
