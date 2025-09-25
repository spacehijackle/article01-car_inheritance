package inheritance.car.power;

import inheritance.car.ACMode;
import inheritance.car.MotivePower;

/**
 * ハイブリッド・システムのクラス
 */
public class HybridPower extends MotivePower
{
    /** 現在の有効な動力源 */
    private MotivePower active;

    /** ガソリンエンジン */
    private final GasolineEngine engine;

    /** 電動モーター */
    private final ElectricMotor motor;

    public HybridPower(GasolineEngine engine, ElectricMotor motor)
    {
        super(0.0d, 0.0d);

        this.engine = engine;
        this.motor = motor;

        // 動力源の選択
        active = (motor.isEmpty() ? engine : motor);
    }

    @Override
    public String powerSource()
    {
        return active.powerSource();
    }

    @Override
    public double capacity()
    {
        return engine.capacity() + motor.capacity();
        //return active.capacity();  // 個別の動力源でモニターしたい場合はこちら
    }

    @Override
    public double remains()
    {
        return engine.remains() + motor.remains();
        //return active.remains();  // 個別の動力源でモニターしたい場合はこちら
    }

    /**
     * @inheritDoc
     * @apiNote 空実装。当クラスの作成の際に、必要に応じて
     *          事前に各々の {@code MotivePower} の fillUp() しておくこと。
     */
    @Override
    public void fillUp() { }

    @Override
    public boolean isEmpty()
    {
        return engine.isEmpty() && motor.isEmpty();
    }

    @Override
    public void consumeEvery1km()
    {
        // 現在の動力源で1km走行
		active.consumeEvery1km();

		// エンジン走行時、発電機を作動させバッテリーに給電
		if(active == engine)
		{
            // モーターに充電の空きがあれば、1km走行あたり0.02kWh充電
            motor.chargeIfPossible(0.02d);
		}

		// 動力源の切り替え
		// ※閾値を下回ったら切り替え
		var threshold = active.capacity() * 0.2d;
		if(active.remains() < threshold)
		{
			switchToAnotherIfPossible();
		}
    }

    @Override
    protected void onModeChanged(ACMode mode)
    {
        engine.onModeChanged(mode);
        motor.onModeChanged(mode);
    }

    /**
     * 現在の動力源を（空でない）別の動力源に切り替える。
     */
    private void switchToAnotherIfPossible()
    {
		var tmpActive = (active == engine ? motor : engine);
		if(!tmpActive.isEmpty())
		{
			// 空でなければ切り替え
			active = tmpActive;
		}
    }
}
