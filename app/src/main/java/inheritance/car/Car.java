package inheritance.car;

import java.util.function.BiConsumer;

/**
 * 自動車クラス
 */
public final class Car
{
    /** 動力源（直接公開で {@code MotivePower} の属性変更が嫌ならクローン提供メソッド作成）*/
	public final MotivePower power;

	/** 空調モード */
	private ACMode mode = ACMode.OFF;

	/**
	 * 動力源を指定して車を生成する。
	 * 
	 * @param power 動力源
	 */
	public Car(MotivePower power)
	{
		this.power = power;
	}

	/** 空調モードを返す。*/
	public ACMode mode() { return mode; }

	/**
	 * 空調モードを変更する。
	 * 
     * @param newMode 空調モード
	 */
	public void changeMode(ACMode newMode)
	{
		if(this.mode != newMode)
		{
			this.mode = newMode;
			power.onModeChanged(newMode);  // 動力源にモード変更通知
		}
	}

    /**
     * 目的地まで車を走らせる。
     * 
     * @param distanceToGo 目的地までの距離(km)
     * @param onEnergyConsumed エネルギー消費イベントハンドラ（1kmごとに呼出し）
     */
	public void move(int distanceToGo, BiConsumer<Car, Integer> onEnergyConsumed)
	{
		for(int mileage=1; mileage<=distanceToGo; mileage++)
		{
			power.consumeEvery1km();

			// イベント通知
			onEnergyConsumed.accept(this, mileage);

            // 動力源が空なら終了
			if(power.isEmpty()) break;
		}
	}
}
