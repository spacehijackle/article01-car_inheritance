package inheritance.car.power;

import inheritance.car.ACMode;
import inheritance.car.MotivePower;

/**
 * 電動モーターのクラス
 */
public class ElectricMotor extends MotivePower
{
    /** 初期電費(km/kWh) */
    private double initConsumptionRate;

    /**
     * 電動モーターを生成する。
     * 
     * @param batteryCapacity バッテリー容量(kWh)
     * @param consumptionRate 電費(km/kWh)
     * @param mode 走行時モード
     */
    public ElectricMotor(double batteryCapacity, double consumptionRate)
    {
        super(batteryCapacity, consumptionRate);
        this.initConsumptionRate = consumptionRate;
    }

    @Override
    public String powerSource() { return "Motor"; }

    /**
     * バッテリーに充電する。
     * 
     * @param increment 充電量(kWh)
     * @return true: 充電成功、false: 充電失敗
     */
    public boolean chargeIfPossible(double increment)
    {
        return injectEnergyIfPossible(increment);
    }

   	@Override
	protected void onModeChanged(ACMode mode)
	{
		var consumptionRate = switch(mode)
		{
			case OFF    -> initConsumptionRate;
			case HEATING -> initConsumptionRate * 0.8d;  // ヒーターONで20%悪化
		};

        // 電費変更
        changeConsumptionRate(consumptionRate);
	}
}
