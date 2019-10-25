package com.polidea.blemulator.bridging;

import android.support.annotation.Nullable;
import android.util.Log;

import com.polidea.blemulator.DeviceContainer;
import com.polidea.blemulator.bridging.constants.ArgumentName;
import com.polidea.blemulator.bridging.constants.DartMethodName;
import com.polidea.blemulator.bridging.constants.SimulationArgumentName;
import com.polidea.blemulator.bridging.decoder.CharacteristicDartValueDecoder;
import com.polidea.blemulator.bridging.decoder.ServiceDartValueDecoder;
import com.polidea.multiplatformbleadapter.Characteristic;
import com.polidea.multiplatformbleadapter.ConnectionOptions;
import com.polidea.multiplatformbleadapter.Device;
import com.polidea.multiplatformbleadapter.OnErrorCallback;
import com.polidea.multiplatformbleadapter.OnSuccessCallback;
import com.polidea.multiplatformbleadapter.Service;
import com.polidea.multiplatformbleadapter.utils.Base64Converter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.flutter.plugin.common.MethodChannel;

public class DartMethodCaller {

    private static final String TAG = DartMethodCaller.class.getSimpleName();
    private MethodChannel dartMethodChannel;
    private JSONToBleErrorConverter jsonToBleErrorConverter = new JSONToBleErrorConverter();
    private CharacteristicDartValueDecoder characteristicJsonDecoder = new CharacteristicDartValueDecoder();
    private ServiceDartValueDecoder serviceDartValueDecoder = new ServiceDartValueDecoder();

    public DartMethodCaller(MethodChannel dartMethodChannel) {
        this.dartMethodChannel = dartMethodChannel;
    }

    public void createClient() {
        HashMap<String, Object> arguments = new HashMap<>();
        dartMethodChannel.invokeMethod(DartMethodName.CREATE_CLIENT,
                arguments,
                new MethodChannel.Result() {
                    @Override
                    public void success(@Nullable Object o) {
                        Log.d(TAG, "createClient SUCCESS");
                    }

                    @Override
                    public void error(String s, @Nullable String s1, @Nullable Object o) {
                        Log.e(TAG, s);
                    }

                    @Override
                    public void notImplemented() {
                        Log.e(TAG, "CREATE CLIENT not implemented");
                    }
                });
    }

    public void destroyClient() {
        dartMethodChannel.invokeMethod(DartMethodName.DESTROY_CLIENT,
                null,
                new MethodChannel.Result() {
                    @Override
                    public void success(@Nullable Object o) {
                        Log.d(TAG, "destroyClient SUCCESS");
                    }

                    @Override
                    public void error(String s, @Nullable String s1, @Nullable Object o) {
                        Log.e(TAG, s + " " + s1);
                    }

                    @Override
                    public void notImplemented() {
                        Log.e(TAG, "DESTROY CLIENT not implemented");
                    }
                });
    }

    public void startDeviceScan() {
        HashMap<String, Object> arguments = new HashMap<>();
        dartMethodChannel.invokeMethod(DartMethodName.START_DEVICE_SCAN,
                arguments,
                new MethodChannel.Result() {
                    @Override
                    public void success(@Nullable Object o) {
                        Log.d(TAG, "startDeviceScan SUCCESS");
                    }

                    @Override
                    public void error(String s, @Nullable String s1, @Nullable Object o) {
                        Log.e(TAG, s);
                    }

                    @Override
                    public void notImplemented() {
                        Log.e(TAG, "START DEVICE SCAN not implemented");
                    }
                });
    }

    public void stopDeviceScan() {
        dartMethodChannel.invokeMethod(DartMethodName.STOP_DEVICE_SCAN,
                null,
                new MethodChannel.Result() {
                    @Override
                    public void success(@Nullable Object o) {
                        Log.d(TAG, "stopDeviceScan SUCCESS");
                    }

                    @Override
                    public void error(String s, @Nullable String s1, @Nullable Object o) {
                        Log.e(TAG, s);
                    }

                    @Override
                    public void notImplemented() {
                        Log.e(TAG, "STOP DEVICE SCAN not implemented");
                    }
                });
    }

    public void connectToDevice(final String deviceIdentifier,
                                final String name,
                                ConnectionOptions connectionOptions,
                                final OnSuccessCallback<Device> onSuccessCallback,
                                final OnErrorCallback onErrorCallback) {
        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put(SimulationArgumentName.DEVICE_ID, deviceIdentifier);
        dartMethodChannel.invokeMethod(DartMethodName.CONNECT_TO_DEVICE, arguments, new MethodChannel.Result() {
            @Override
            public void success(@Nullable Object o) {
                Log.d(TAG, "connectToDevice SUCCESS");
                onSuccessCallback.onSuccess(new Device(deviceIdentifier, name));
            }

            @Override
            public void error(String s, @Nullable String s1, @Nullable Object o) {
                Log.e(TAG, "connectToDevice error "+ s1);
                onErrorCallback.onError(jsonToBleErrorConverter.bleErrorFromJSON(s1));
            }

            @Override
            public void notImplemented() {
                Log.e(TAG, "CONNECT TO DEVICE not implemented");
            }
        });
    }

    public void isDeviceConnected(String deviceIdentifier,
                                  final OnSuccessCallback<Boolean> onSuccessCallback,
                                  final OnErrorCallback onErrorCallback) {
        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put(SimulationArgumentName.DEVICE_ID, deviceIdentifier);
        dartMethodChannel.invokeMethod(DartMethodName.IS_DEVICE_CONNECTED, arguments, new MethodChannel.Result() {
            @Override
            public void success(@Nullable Object o) {
                Log.d(TAG, "connectToDevice SUCCESS");
                onSuccessCallback.onSuccess((Boolean) o);
            }

            @Override
            public void error(String s, @Nullable String s1, @Nullable Object o) {
                Log.e(TAG, s);
                onErrorCallback.onError(jsonToBleErrorConverter.bleErrorFromJSON(s1));
            }

            @Override
            public void notImplemented() {
                Log.e(TAG, "CONNECT TO DEVICE not implemented");
            }
        });
    }

    public void disconnectOrCancelConnection(final String deviceIdentifier,
                                             final String name,
                                             final OnSuccessCallback<Device> onSuccessCallback,
                                             final OnErrorCallback onErrorCallback) {
        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put(SimulationArgumentName.DEVICE_ID, deviceIdentifier);
        dartMethodChannel.invokeMethod(DartMethodName.DISCONNECT_OR_CANCEL_CONNECTION, arguments, new MethodChannel.Result() {
            @Override
            public void success(@Nullable Object o) {
                Log.d(TAG, "connectToDevice SUCCESS");
                onSuccessCallback.onSuccess(new Device(deviceIdentifier, name));
            }

            @Override
            public void error(String errorCode, @Nullable String jsonBody, @Nullable Object irrelevant) {
                onErrorCallback.onError(jsonToBleErrorConverter.bleErrorFromJSON(jsonBody));
            }

            @Override
            public void notImplemented() {
                Log.e(TAG, "CONNECT TO DEVICE not implemented");
            }
        });
    }

    public void discoverAllServicesAndCharacteristicsForDevice(
            final String deviceIdentifier,
            final String name,
            final String transactionId,
            final OnSuccessCallback<DeviceContainer> onSuccessCallback,
            final OnErrorCallback onErrorCallback) {
        dartMethodChannel.invokeMethod(DartMethodName.DISCOVER_ALL_SERVICES_AND_CHARACTERISTICS, new HashMap<String, Object>() {{
            put(ArgumentName.IDENTIFIER, deviceIdentifier);
            put(ArgumentName.TRANSACTION_ID, transactionId);
        }}, new MethodChannel.Result() {
            @Override
            public void success(@Nullable Object discoveryResponse) {
                onSuccessCallback.onSuccess(parseDiscoveryResponse(deviceIdentifier, name, discoveryResponse));
            }

            @Override
            public void error(String errorCode, @Nullable String jsonBody, @Nullable Object irrelevant) {
                onErrorCallback.onError(jsonToBleErrorConverter.bleErrorFromJSON(jsonBody));
            }

            @Override
            public void notImplemented() {
                Log.e(TAG, DartMethodName.DISCOVER_ALL_SERVICES_AND_CHARACTERISTICS + " not implemented");
            }
        });
    }

    private DeviceContainer parseDiscoveryResponse(String deviceIdentifier, String deviceName, Object responseObject) {
        List<Map<String, Object>> response = (List<Map<String, Object>>) responseObject;
        List<Service> services = new ArrayList<>();
        Map<String, List<Characteristic>> characteristics = new HashMap<>();
        for (Map<String, Object> mappedService : response) {
            Service service = serviceDartValueDecoder.decode(deviceIdentifier, mappedService);
            services.add(service);
            characteristics.put((String) mappedService.get(SimulationArgumentName.SERVICE_UUID),
                    parseCharacteristicsForServicesResponse(
                            (List<Map<String, Object>>) mappedService.get(SimulationArgumentName.CHARACTERISTICS))
            );
        }
        return new DeviceContainer(deviceIdentifier, deviceName, services, characteristics);
    }

    private List<Characteristic> parseCharacteristicsForServicesResponse(List<Map<String, Object>> response) {
        List<Characteristic> characteristics = new ArrayList<>();
        for (Map<String, Object> mappedCharacteristic : response) {
            characteristics.add(characteristicJsonDecoder.decode(mappedCharacteristic));
        }
        return characteristics;
    }

    public void readCharacteristicForDevice(
            final String deviceIdentifier,
            final String serviceUUID,
            final String characteristicUUID,
            final String transactionId,
            final OnSuccessCallback<Characteristic> onSuccessCallback,
            final OnErrorCallback onErrorCallback) {
        HashMap<String, Object> arguments = new HashMap<String, Object>() {{
            put(ArgumentName.DEVICE_IDENTIFIER, deviceIdentifier);
            put(ArgumentName.SERVICE_UUID, serviceUUID);
            put(ArgumentName.CHARACTERISTIC_UUID, characteristicUUID);
            put(ArgumentName.TRANSACTION_ID, transactionId);
        }};
        dartMethodChannel.invokeMethod(DartMethodName.READ_CHARACTERISTIC_FOR_DEVICE,
                arguments,
                new MethodChannel.Result() {
                    @Override
                    public void success(@Nullable Object characteristicJsonObject) {
                        onSuccessCallback.onSuccess(characteristicJsonDecoder.decode((Map<String, Object>) characteristicJsonObject));
                    }

                    @Override
                    public void error(String errorCode, @Nullable String jsonBody, @Nullable Object irrelevant) {
                        onErrorCallback.onError(jsonToBleErrorConverter.bleErrorFromJSON(jsonBody));
                    }

                    @Override
                    public void notImplemented() {
                        Log.e(TAG, "readCharacteristicForDevice not implemented");
                    }
                });
    }

    public void readCharacteristicForService(
            final int serviceIdentifier,
            final String characteristicUUID,
            final String transactionId,
            final OnSuccessCallback<Characteristic> onSuccessCallback,
            final OnErrorCallback onErrorCallback) {
        HashMap<String, Object> arguments = new HashMap<String, Object>() {{
            put(ArgumentName.SERVICE_IDENTIFIER, serviceIdentifier);
            put(ArgumentName.CHARACTERISTIC_UUID, characteristicUUID);
            put(ArgumentName.TRANSACTION_ID, transactionId);
        }};
        dartMethodChannel.invokeMethod(DartMethodName.READ_CHARACTERISTIC_FOR_SERVICE,
                arguments,
                new MethodChannel.Result() {
                    @Override
                    public void success(@Nullable Object characteristicJsonObject) {
                        onSuccessCallback.onSuccess(characteristicJsonDecoder.decode((Map<String, Object>) characteristicJsonObject));
                    }

                    @Override
                    public void error(String errorCode, @Nullable String jsonBody, @Nullable Object irrelevant) {
                        onErrorCallback.onError(jsonToBleErrorConverter.bleErrorFromJSON(jsonBody));
                    }

                    @Override
                    public void notImplemented() {
                        Log.e(TAG, "readCharacteristicForService not implemented");
                    }
                });
    }

    public void readCharacteristic(
            final int characteristicIdentifier,
            final String transactionId,
            final OnSuccessCallback<Characteristic> onSuccessCallback,
            final OnErrorCallback onErrorCallback) {
        HashMap<String, Object> arguments = new HashMap<String, Object>() {{
            put(ArgumentName.CHARACTERISTIC_IDENTIFIER, characteristicIdentifier);
            put(ArgumentName.TRANSACTION_ID, transactionId);
        }};
        dartMethodChannel.invokeMethod(DartMethodName.READ_CHARACTERISTIC_FOR_IDENTIFIER,
                arguments,
                new MethodChannel.Result() {
                    @Override
                    public void success(@Nullable Object characteristicJsonObject) {
                        onSuccessCallback.onSuccess(characteristicJsonDecoder.decode((Map<String, Object>) characteristicJsonObject));
                    }

                    @Override
                    public void error(String errorCode, @Nullable String jsonBody, @Nullable Object irrelevant) {
                        onErrorCallback.onError(jsonToBleErrorConverter.bleErrorFromJSON(jsonBody));
                    }

                    @Override
                    public void notImplemented() {
                        Log.e(TAG, "readCharacteristic not implemented");
                    }
                });
    }

    public void writeCharacteristicForDevice(
            final String deviceIdentifier,
            final String serviceUUID,
            final String characteristicUUID,
            final String valueBase64,
            final String transactionId,
            final OnSuccessCallback<Characteristic> onSuccessCallback,
            final OnErrorCallback onErrorCallback) {
        HashMap<String, Object> arguments = new HashMap<String, Object>() {{
            put(ArgumentName.DEVICE_IDENTIFIER, deviceIdentifier);
            put(ArgumentName.SERVICE_UUID, serviceUUID);
            put(ArgumentName.CHARACTERISTIC_UUID, characteristicUUID);
            put(ArgumentName.VALUE, Base64Converter.decode(valueBase64));
            put(ArgumentName.TRANSACTION_ID, transactionId);
        }};
        dartMethodChannel.invokeMethod(DartMethodName.WRITE_CHARACTERISTIC_FOR_DEVICE,
                arguments,
                new MethodChannel.Result() {
                    @Override
                    public void success(@Nullable Object characteristicJsonObject) {
                        onSuccessCallback.onSuccess(characteristicJsonDecoder.decode((Map<String, Object>) characteristicJsonObject));
                    }

                    @Override
                    public void error(String errorCode, @Nullable String jsonBody, @Nullable Object irrelevant) {
                        onErrorCallback.onError(jsonToBleErrorConverter.bleErrorFromJSON(jsonBody));
                    }

                    @Override
                    public void notImplemented() {
                        Log.e(TAG, "writeCharacteristicForDevice not implemented");
                    }
                });
    }

    public void writeCharacteristicForService(
            final int serviceIdentifier,
            final String characteristicUUID,
            final String valueBase64,
            final String transactionId,
            final OnSuccessCallback<Characteristic> onSuccessCallback,
            final OnErrorCallback onErrorCallback) {
        HashMap<String, Object> arguments = new HashMap<String, Object>() {{
            put(ArgumentName.SERVICE_IDENTIFIER, serviceIdentifier);
            put(ArgumentName.CHARACTERISTIC_UUID, characteristicUUID);
            put(ArgumentName.VALUE, Base64Converter.decode(valueBase64));
            put(ArgumentName.TRANSACTION_ID, transactionId);
        }};
        dartMethodChannel.invokeMethod(DartMethodName.WRITE_CHARACTERISTIC_FOR_SERVICE,
                arguments,
                new MethodChannel.Result() {
                    @Override
                    public void success(@Nullable Object characteristicJsonObject) {
                        onSuccessCallback.onSuccess(characteristicJsonDecoder.decode((Map<String, Object>) characteristicJsonObject));
                    }

                    @Override
                    public void error(String errorCode, @Nullable String jsonBody, @Nullable Object irrelevant) {
                        onErrorCallback.onError(jsonToBleErrorConverter.bleErrorFromJSON(jsonBody));
                    }

                    @Override
                    public void notImplemented() {
                        Log.e(TAG, "writeCharacteristicForService not implemented");
                    }
                });
    }

    public void writeCharacteristic(
            final int characteristicIdentifier,
            final String valueBase64,
            final String transactionId,
            final OnSuccessCallback<Characteristic> onSuccessCallback,
            final OnErrorCallback onErrorCallback) {
        HashMap<String, Object> arguments = new HashMap<String, Object>() {{
            put(ArgumentName.CHARACTERISTIC_IDENTIFIER, characteristicIdentifier);
            put(ArgumentName.VALUE, Base64Converter.decode(valueBase64));
            put(ArgumentName.TRANSACTION_ID, transactionId);
        }};
        dartMethodChannel.invokeMethod(DartMethodName.WRITE_CHARACTERISTIC_FOR_IDENTIFIER,
                arguments,
                new MethodChannel.Result() {
                    @Override
                    public void success(@Nullable Object characteristicJsonObject) {
                        onSuccessCallback.onSuccess(characteristicJsonDecoder.decode((Map<String, Object>) characteristicJsonObject));
                    }

                    @Override
                    public void error(String errorCode, @Nullable String jsonBody, @Nullable Object irrelevant) {
                        onErrorCallback.onError(jsonToBleErrorConverter.bleErrorFromJSON(jsonBody));
                    }

                    @Override
                    public void notImplemented() {
                        Log.e(TAG, "writeCharacteristic not implemented");
                    }
                });
    }

    public void monitorCharacteristicForDevice(
            final String deviceIdentifier,
            final String serviceUUID,
            final String characteristicUUID,
            final String transactionId,
            final OnErrorCallback onErrorCallback) {
        HashMap<String, Object> arguments = new HashMap<String, Object>() {{
            put(ArgumentName.DEVICE_IDENTIFIER, deviceIdentifier);
            put(ArgumentName.SERVICE_UUID, serviceUUID);
            put(ArgumentName.CHARACTERISTIC_UUID, characteristicUUID);
            put(ArgumentName.TRANSACTION_ID, transactionId);
        }};
        dartMethodChannel.invokeMethod(DartMethodName.MONITOR_CHARACTERISTIC_FOR_DEVICE,
                arguments,
                new MethodChannel.Result() {
                    @Override
                    public void success(@Nullable Object characteristicJsonObject) {
                        Log.i(TAG, "monitorCharacteristicForDevice SUCCESS");
                    }

                    @Override
                    public void error(String errorCode, @Nullable String jsonBody, @Nullable Object irrelevant) {
                        onErrorCallback.onError(jsonToBleErrorConverter.bleErrorFromJSON(jsonBody));
                    }

                    @Override
                    public void notImplemented() {
                        Log.e(TAG, "monitorCharacteristicForDevice not implemented");
                    }
                });
    }

    public void monitorCharacteristicForService(
            final int serviceIdentifier,
            final String characteristicUUID,
            final String transactionId,
            final OnErrorCallback onErrorCallback) {
        HashMap<String, Object> arguments = new HashMap<String, Object>() {{
            put(ArgumentName.SERVICE_IDENTIFIER, serviceIdentifier);
            put(ArgumentName.CHARACTERISTIC_UUID, characteristicUUID);
            put(ArgumentName.TRANSACTION_ID, transactionId);
        }};
        dartMethodChannel.invokeMethod(DartMethodName.MONITOR_CHARACTERISTIC_FOR_SERVICE,
                arguments,
                new MethodChannel.Result() {
                    @Override
                    public void success(@Nullable Object characteristicJsonObject) {
                        Log.i(TAG, "monitorCharacteristicForService SUCCESS");
                    }

                    @Override
                    public void error(String errorCode, @Nullable String jsonBody, @Nullable Object irrelevant) {
                        onErrorCallback.onError(jsonToBleErrorConverter.bleErrorFromJSON(jsonBody));
                    }

                    @Override
                    public void notImplemented() {
                        Log.e(TAG, "monitorCharacteristicForService not implemented");
                    }
                });
    }

    public void monitorCharacteristic(
            final int characteristicIdentifier,
            final String transactionId,
            final OnErrorCallback onErrorCallback) {
        HashMap<String, Object> arguments = new HashMap<String, Object>() {{
            put(ArgumentName.CHARACTERISTIC_IDENTIFIER, characteristicIdentifier);
            put(ArgumentName.TRANSACTION_ID, transactionId);
        }};
        dartMethodChannel.invokeMethod(DartMethodName.MONITOR_CHARACTERISTIC_FOR_IDENTIFIER,
                arguments,
                new MethodChannel.Result() {
                    @Override
                    public void success(@Nullable Object characteristicJsonObject) {
                        Log.i(TAG, "monitorCharacteristic SUCCESS");
                    }

                    @Override
                    public void error(String errorCode, @Nullable String jsonBody, @Nullable Object irrelevant) {
                        onErrorCallback.onError(jsonToBleErrorConverter.bleErrorFromJSON(jsonBody));
                    }

                    @Override
                    public void notImplemented() {
                        Log.e(TAG, "monitorCharacteristic not implemented");
                    }
                });
    }

    public void readRSSIForDevice(final String deviceIdentifier,
                                  final OnSuccessCallback<Device> onSuccessCallback,
                                  final OnErrorCallback onErrorCallback) {
        HashMap<String, Object> arguments = new HashMap<String, Object>();
        arguments.put(SimulationArgumentName.DEVICE_ID, deviceIdentifier);
        dartMethodChannel.invokeMethod(DartMethodName.RSSI, arguments, new MethodChannel.Result() {
            @Override
            public void success(@Nullable Object rssi) {
                Log.d(TAG, "Fetch RSSI");
                String placeholderName = "Simulated device"; // the name does not matter but API of MBA requires it
                Device device = new Device(deviceIdentifier, placeholderName);
                device.setRssi((int) rssi);
                onSuccessCallback.onSuccess(device);
            }

            @Override
            public void error(String s, @Nullable String s1, @Nullable Object o) {
                Log.e(TAG, "Error during reading rssi of simulating peripheral: " + s1);
                onErrorCallback.onError(jsonToBleErrorConverter.bleErrorFromJSON(s1));
            }

            @Override
            public void notImplemented() {
                Log.e(TAG, "readRSSIForDevice not implemented");
            }
        });
    }

    public void cancelTransaction(final String transactionId) {
        Log.i(TAG, "cancelTransaction");
        dartMethodChannel.invokeMethod(DartMethodName.CANCEL_TRANSACTION, new HashMap<String, String>() {{
            put(ArgumentName.TRANSACTION_ID, transactionId);
        }});
    }
}