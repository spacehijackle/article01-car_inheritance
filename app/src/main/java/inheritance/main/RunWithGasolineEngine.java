package inheritance.main;

import java.util.function.BiConsumer;

import inheritance.car.Car;
import inheritance.car.power.GasolineEngine;
import inheritance.monitor.Dashboard;

/**
 * ガソリン車走行クラス
 * 
 * @author t.yoshida
 */
public class RunWithGasolineEngine
{
    public static void main(String[] args)
    {
        int distanceToGo = 300; // 目的地までの距離(km)

        // エネルギー消費イベントハンドラ
        BiConsumer<Car, Integer> onEnergyConsumed = (car, mileage) ->
        {
            // 走行状況の表示
            Dashboard.showStatus(car, distanceToGo, mileage);

            // 表示インターバル
            try { Thread.sleep(100); } catch(InterruptedException ex) { }
        };

        //
        // ガソリン車で目的地まで走らせる
        //
        var engineCar = new Car
        (
            new GasolineEngine(40, 12.5d)
            {
                {
                    fillUp();  // 満タン
                }
            }
        );
        engineCar.move(distanceToGo, onEnergyConsumed);
    }
}
