package Q2;
import  java.util.Scanner;

public class PacketSymmetryAuditor 
{

    public PacketSymmetryAuditor()
    {


    }
/** Keeps only letters/digits and lower-cases them (ignores separators/case). */
    private static String normalize(String raw) 
    {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < raw.length(); i++) 
        {
            char c = raw.charAt(i);
            if (Character.isLetterOrDigit(c)) 
            {
                sb.append(Character.toLowerCase(c));
            }
        }
        return sb.toString();
    }
 
    private static void audit(String rawPayload) 
    {
        String payload = normalize(rawPayload);
        int n = payload.length();
 
        System.out.println("-------------------------------------------------");
        System.out.println("[AUDIT] Raw payload       : \"" + rawPayload + "\"");
        System.out.println("[AUDIT] Normalized payload: \"" + payload + "\"");
 
        if (n == 0) 
        {
            System.out.println("[RESULT] Empty payload - nothing to verify.");
            return;
        }
 
        TransactionStack stack = new TransactionStack(n);
        Requestqueue queue = new Requestqueue(n);
 
        // Ingest: push into the stack while simultaneously enqueuing.
        for (int i = 0; i < n; i++) 
        {
            char c = payload.charAt(i);
            stack.push(c);
            queue.enqueue(c);
        }
 
        // Verify: pop and dequeue together, comparing each pair.
        boolean symmetric = true;
        int step = 1;
        while (!stack.isEmpty() && !queue.isEmpty()) //iterate until both contains elements
        {
            char fromStack = stack.pop();       // last-in  (reverse stream)
            char fromQueue = queue.dequeue();   // first-in (forward stream)
            boolean match = (fromStack == fromQueue);
            System.out.println("  Step " + step++ + ": POP='" + fromStack
                    + "'  DEQUEUE='" + fromQueue + "'  -> " + (match ? "MATCH" : "MISMATCH"));
            if (!match) {
                symmetric = false;
                break;
            }
        }
 
        System.out.println(symmetric ? "[RESULT] VERIFIED: packet exhibits bi-directional symmetry (palindrome signature)." : "[RESULT] REJECTED: packet is NOT symmetric.");
    }
 
    public static void main(String[] args) 
    {
        if (args.length > 0) 
        {
            for (String payload : args)
            {
                audit(payload);
            }
            return;
        }
 
        Scanner in = new Scanner(System.in);
        System.out.print("Enter raw packet payload: ");
        String payload = in.hasNextLine() ? in.nextLine() : "";
        audit(payload);
        in.close();
    }
    
}
