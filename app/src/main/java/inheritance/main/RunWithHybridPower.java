package inheritance.main;

import java.util.function.BiConsumer;

import inheritance.car.ACMode;
import inheritance.car.Car;
import inheritance.car.power.ElectricMotor;
import inheritance.car.power.GasolineEngine;
import inheritance.car.power.HybridPower;
import inheritance.monitor.Dashboard;

/**
 * ハイブリッド車走行クラス
 * 
 * @author t.yoshida
 */
public class RunWithHybridPower
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
        // ハイブリッド車で目的地まで走らせる
        //
        var hybridCar = new Car
        (
            new HybridPower
            (
                new GasolineEngine(12, 15.0d)
                {
                    {
                        fillUp();  // 満タン
                    }
                },
                new ElectricMotor(20, 5.0d)
                {
                    {
                        fillUp();  // 満充電
                    }
                }
            )
        );
        hybridCar.changeMode(ACMode.HEATING);  // 空調モードをヒーターONに変更
        hybridCar.move(distanceToGo, onEnergyConsumed);
    }
}
