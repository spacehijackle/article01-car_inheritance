package inheritance.car;

import java.util.function.BiConsumer;

/**
 * 自動車クラス
 */
public final class Car
{
    /** 動力源 */
	private final MotivePower power;

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

	/** 動力源のプロパティを返す。*/
	public RefMotivePower power() { return new RefMotivePower(); }

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

	/**
	 * 動力源のプロパティ（参照のみ）提供クラス
	 */
	public class RefMotivePower
	{
		/** 動力源の名称を返す。*/
		public String powerSource() { return power.powerSource(); }

		/** 動力源の容量を返す。*/
		public double capacity() { return power.capacity(); }

		/** 動力源の残量を返す。*/
		public double remains() { return power.remains(); }
	}
}
