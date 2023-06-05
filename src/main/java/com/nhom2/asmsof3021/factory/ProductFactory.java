package com.nhom2.asmsof3021.factory;

import com.nhom2.asmsof3021.model.Computer;
import com.nhom2.asmsof3021.model.Laptop;
import com.nhom2.asmsof3021.model.Monitor;
import com.nhom2.asmsof3021.model.Product;
import com.nhom2.asmsof3021.model.accessory.*;
import com.nhom2.asmsof3021.model.gear.*;

public interface ProductFactory {
    Product createProduct();
    public class ComputerCasesFactory implements ProductFactory {
        @Override
        public Product createProduct() {
            return new ComputerCases();
        }
    }

    public class CPUFactory implements ProductFactory {
        @Override
        public Product createProduct() {
            return new CPU();
        }
    }

    public class CPUCoolerFactory implements ProductFactory {
        @Override
        public Product createProduct() {
            return new CPUCooler();
        }
    }

    public class FanFactory implements ProductFactory {
        @Override
        public Product createProduct() {
            return new Fan();
        }
    }

    public class MainboardFactory implements ProductFactory {
        @Override
        public Product createProduct() {
            return new Mainboard();
        }
    }

    public class RamFactory implements ProductFactory {
        @Override
        public Product createProduct() {
            return new Ram();
        }
    }

    public class VGAFactory implements ProductFactory {
        @Override
        public Product createProduct() {
            return new VGA();
        }
    }

    public class GamepadFactory implements ProductFactory {
        @Override
        public Product createProduct() {
            return new Gamepad();
        }
    }

    public class HeadphoneFactory implements ProductFactory {
        @Override
        public Product createProduct() {
            return new Headphone();
        }
    }

    public class MechanicalKeyboardFactory implements ProductFactory {
        @Override
        public Product createProduct() {
            return new MechanicalKeyboard();
        }
    }

    public class MicrophoneFactory implements ProductFactory {
        @Override
        public Product createProduct() {
            return new Microphone();
        }
    }

    public class MousePadFactory implements ProductFactory {
        @Override
        public Product createProduct() {
            return new MousePad();
        }
    }

    public class OpticalMouseFactory implements ProductFactory {
        @Override
        public Product createProduct() {
            return new OpticalMouse();
        }
    }

    public class SpeakerFactory implements ProductFactory {
        @Override
        public Product createProduct() {
            return new Speaker();
        }
    }

    public class WebcamFactory implements ProductFactory {
        @Override
        public Product createProduct() {
            return new Webcam();
        }
    }

    public class ComputerFactory implements ProductFactory {
        @Override
        public Product createProduct() {
            return new Computer();
        }
    }

    public class LaptopFactory implements ProductFactory {
        @Override
        public Product createProduct() {
            return new Laptop();
        }
    }

    public class MonitorFactory implements ProductFactory {
        @Override
        public Product createProduct() {
            return new Monitor();
        }
    }
}
