import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.Target;
import org.snmp4j.TransportMapping;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.*;
import org.snmp4j.transport.DefaultUdpTransportMapping;

import static jdk.nashorn.internal.runtime.JSType.isNumber;


/**
 * Created by Daniil on 18.04.2017.
 */
public class Program {
    private final static String SNMP_COMMUNITY = "public";
    private final static int    SNMP_RETRIES   = 3;
    private final static long   SNMP_TIMEOUT   = 1000L;
    private final static String PATH_INTERFACES ="C:\\Users\\Daniil\\Desktop\\SnmpTask\\Interfaces.txt";
    private Snmp snmp = null;
    private TransportMapping transport = null;





    private void test(String ip, String port) throws IOException {
        Target t = getTarget("udp:" + ip + "/" + port);
        //Target t = getTarget("udp:127.0.0.1/175");
        String prev=send(t, "1.3.6.1.2.1.2.2.1.1.0");
        System.out.println(prev.substring(prev.indexOf("=")+2));
        String descr;
        String type;
        String mtu;
        String speed;
        String physAddress;
        String adminStatus;
        String operStatus;
        String lastChange;
        String inOctets;
        String inUcast;
        String inDiscards;
        String inErrors;
        String inUnknown;
        String outOctets;
        String outUcast;
        String outDiscards;
        String outErrors;


        String subR;
        String inform;
        try(FileWriter writer = new FileWriter(PATH_INTERFACES,false)) {

            subR = prev.substring(prev.indexOf("=") + 2);
            Integer currInd = Integer.parseInt(prev.substring(prev.indexOf("=") + 2));
            descr = send(t, "1.3.6.1.2.1.2.2.1.2." + (currInd - 1));
            type = send(t, "1.3.6.1.2.1.2.2.1.3." + (currInd - 1));
            mtu = send(t, "1.3.6.1.2.1.2.2.1.4." + (currInd - 1));
            speed = send(t, "1.3.6.1.2.1.2.2.1.5." + (currInd - 1));
            physAddress = send(t, "1.3.6.1.2.1.2.2.1.6." + (currInd - 1));
            adminStatus = send(t, "1.3.6.1.2.1.2.2.1.7." + (currInd - 1));
            operStatus = send(t, "1.3.6.1.2.1.2.2.1.8." + (currInd - 1));
            lastChange = send(t, "1.3.6.1.2.1.2.2.1.9." + (currInd - 1));
            inOctets = send(t, "1.3.6.1.2.1.2.2.1.10." + (currInd - 1));
            inUcast = send(t, "1.3.6.1.2.1.2.2.1.11." + (currInd - 1));
            inDiscards = send(t, "1.3.6.1.2.1.2.2.1.13." + (currInd - 1));
            inErrors = send(t, "1.3.6.1.2.1.2.2.1.14." + (currInd - 1));
            inUnknown = send(t, "1.3.6.1.2.1.2.2.1.15." + (currInd - 1));
            outOctets = send(t, "1.3.6.1.2.1.2.2.1.16." + (currInd - 1));
            outUcast = send(t, "1.3.6.1.2.1.2.2.1.17." + (currInd - 1));
            outDiscards = send(t, "1.3.6.1.2.1.2.2.1.19." + (currInd - 1));
            outErrors = send(t, "1.3.6.1.2.1.2.2.1.20." + (currInd - 1));

            inform=("Interface " + prev.substring(prev.indexOf("=") + 2) + ": Description="
                    + descr.substring(descr.indexOf("=") + 2) + "  Type=" + type.substring(type.indexOf("=") + 2) +
                    "  MTU=" + mtu.substring(mtu.indexOf("=") + 2) + "  Speed=" + speed.substring(speed.indexOf("=") + 2) +
                    "  PhysAdress=" + physAddress.substring(physAddress.indexOf("=") + 2) + "  adminStatus=" +
                    adminStatus.substring(adminStatus.indexOf("=") + 2) + "  operStatus=" +
                    operStatus.substring(operStatus.indexOf("=") + 2) + "  LastChange=" + lastChange.substring(lastChange.indexOf("=") + 2)
                    + "  inOctets=" + inOctets.substring(inOctets.indexOf("=") + 2) + "  inUcast=" + inUcast.substring(inUcast.indexOf("=") + 2)
                    + "  inDiscards=" + inDiscards.substring(inDiscards.indexOf("=") + 2) + "  inErrors=" + inErrors.substring(inErrors.indexOf("=") + 2)
                    + "  inUnknown=" + inUnknown.substring(inUnknown.indexOf("=") + 2) + "  outOctets=" + outOctets.substring(outOctets.indexOf("=") + 2)
                    + "  outUcast=" + outUcast.substring(outUcast.indexOf("=") + 2) + "  outDiscards=" + outDiscards.substring(outDiscards.indexOf("=") + 2)
                    + "  outErrors=" + outErrors.substring(outErrors.indexOf("=") + 2));
            System.out.println(inform);
            writer.append(inform +"\r\n");


            for (int i = 0; i <= 80; i++) {
                String r = send(t, "1.3.6.1.2.1.2.2.1.1." + i);
                subR = r.substring(r.indexOf("=") + 2);
                //if (isNumber(r.substring(r.indexOf("=") + 2))){

                //else continue;

                if ((!r.equals(prev)) && (subR.matches("\\d+"))) {

                     currInd = Integer.parseInt(r.substring(r.indexOf("=") + 2));
                    descr = send(t, "1.3.6.1.2.1.2.2.1.2." + (currInd - 1));
                    type = send(t, "1.3.6.1.2.1.2.2.1.3." + (currInd - 1));
                    mtu = send(t, "1.3.6.1.2.1.2.2.1.4." + (currInd - 1));
                    speed = send(t, "1.3.6.1.2.1.2.2.1.5." + (currInd - 1));
                    physAddress = send(t, "1.3.6.1.2.1.2.2.1.6." + (currInd - 1));
                    adminStatus = send(t, "1.3.6.1.2.1.2.2.1.7." + (currInd - 1));
                    operStatus = send(t, "1.3.6.1.2.1.2.2.1.8." + (currInd - 1));
                    lastChange = send(t, "1.3.6.1.2.1.2.2.1.9." + (currInd - 1));
                    inOctets = send(t, "1.3.6.1.2.1.2.2.1.10." + (currInd - 1));
                    inUcast = send(t, "1.3.6.1.2.1.2.2.1.11." + (currInd - 1));
                    inDiscards = send(t, "1.3.6.1.2.1.2.2.1.13." + (currInd - 1));
                    inErrors = send(t, "1.3.6.1.2.1.2.2.1.14." + (currInd - 1));
                    inUnknown = send(t, "1.3.6.1.2.1.2.2.1.15." + (currInd - 1));
                    outOctets = send(t, "1.3.6.1.2.1.2.2.1.16." + (currInd - 1));
                    outUcast = send(t, "1.3.6.1.2.1.2.2.1.17." + (currInd - 1));
                    outDiscards = send(t, "1.3.6.1.2.1.2.2.1.19." + (currInd - 1));
                    outErrors = send(t, "1.3.6.1.2.1.2.2.1.20." + (currInd - 1));

                    inform=("Interface " + r.substring(r.indexOf("=") + 2) + ": Description="
                            + descr.substring(descr.indexOf("=") + 2) + "  Type=" + type.substring(type.indexOf("=") + 2) +
                            "  MTU=" + mtu.substring(mtu.indexOf("=") + 2) + "  Speed=" + speed.substring(speed.indexOf("=") + 2) +
                            "  PhysAdress=" + physAddress.substring(physAddress.indexOf("=") + 2) + "  adminStatus=" +
                            adminStatus.substring(adminStatus.indexOf("=") + 2) + "  operStatus=" +
                            operStatus.substring(operStatus.indexOf("=") + 2) + "  LastChange=" + lastChange.substring(lastChange.indexOf("=") + 2)
                            + "  inOctets=" + inOctets.substring(inOctets.indexOf("=") + 2) + "  inUcast=" + inUcast.substring(inUcast.indexOf("=") + 2)
                            + "  inDiscards=" + inDiscards.substring(inDiscards.indexOf("=") + 2) + "  inErrors=" + inErrors.substring(inErrors.indexOf("=") + 2)
                            + "  inUnknown=" + inUnknown.substring(inUnknown.indexOf("=") + 2) + "  outOctets=" + outOctets.substring(outOctets.indexOf("=") + 2)
                            + "  outUcast=" + outUcast.substring(outUcast.indexOf("=") + 2) + "  outDiscards=" + outDiscards.substring(outDiscards.indexOf("=") + 2)
                            + "  outErrors=" + outErrors.substring(outErrors.indexOf("=") + 2));


                    System.out.println(inform);
                    writer.append(inform +"\r\n");


                    //System.out.println(r);
                    prev = r;
                }

            }
            writer.flush();
        }
        catch (IOException ex){
            System.out.println(ex.getMessage());
        }
    }

    private String send(Target target, String oid) throws IOException {
        PDU pdu = new PDU();
        pdu.add(new VariableBinding(new OID(oid)));
        pdu.setType(PDU.GETNEXT);
        ResponseEvent event = snmp.send(pdu, target, null);
        if (event != null) {
            return event.getResponse().get(0).toString();
        } else {
            return "Timeout exceeded";
        }
    }

    private Target getTarget(String address) {
        Address targetAddress = GenericAddress.parse(address);
        CommunityTarget target = new CommunityTarget();
        target.setCommunity(new OctetString(SNMP_COMMUNITY));
        target.setAddress(targetAddress);
        target.setRetries(SNMP_RETRIES);
        target.setTimeout(SNMP_TIMEOUT);
        target.setVersion(SnmpConstants.version2c);
        return target;
    }

    private void start() throws IOException {
        transport = new DefaultUdpTransportMapping();
        snmp = new Snmp(transport);
        transport.listen();
    }

    private void stop() throws IOException {
        try {
            if (transport != null) {
                transport.close();
                transport = null;
            }
        } finally {
            if (snmp != null) {
                snmp.close();
                snmp = null;
            }
        }
    }

    public static void main(String[] args) {
        Program t = new Program();
        Scanner in = new Scanner(System.in);
        System.out.println("Enter IP");
        String ip = in.nextLine();
        System.out.println(ip);
        System.out.println("Enter port nubmer");
        String port = in.nextLine();
        System.out.println(port);
        try {
            try {
                t.start();
                t.test(ip,port);
            } finally {
                t.stop();
            }
        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }
}


