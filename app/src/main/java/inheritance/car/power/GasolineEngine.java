package inheritance.car.power;

import inheritance.car.ACMode;
import inheritance.car.MotivePower;

/**
 * ガソリンエンジンのクラス
 */
public class GasolineEngine extends MotivePower
{
    /**
     * ガソリンエンジンを生成する。
     * 
     * @param capacity 容量(L)
     * @param consumptionRate 燃費(km/L)
     * @param mode 走行時モード
     */
    public GasolineEngine(double capacity, double consumptionRate)
    {
        super(capacity, consumptionRate);
    }

    @Override
    public String powerSource() { return "Gas Engine"; }

    @Override
    protected void onModeChanged(ACMode mode)
    {
        // モードの変更で燃費は不変
        // ※ヒーターONでもエンジンの排熱を利用するので燃費は変わらない。
    }
}
