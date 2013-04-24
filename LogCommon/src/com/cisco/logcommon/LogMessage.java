package com.cisco.logcommon;

import android.os.Parcel;
import android.os.Parcelable;

public class LogMessage implements Parcelable {
	private int id, level;
	private String tag, text;

	public LogMessage(int id, int level, String tag, String text) {
		super();
		this.id = id;
		this.level = level;
		this.tag = tag;
		this.text = text;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(id);
		dest.writeInt(level);
		dest.writeString(tag);
		dest.writeString(text);
	}

	public static final Parcelable.Creator<LogMessage> CREATOR = new Parcelable.Creator<LogMessage>() {

		@Override
		public LogMessage createFromParcel(Parcel source) {
			return new LogMessage(source.readInt(), source.readInt(),
					source.readString(), source.readString());
		}

		@Override
		public LogMessage[] newArray(int size) {
			return new LogMessage[size];
		}
	};

}
