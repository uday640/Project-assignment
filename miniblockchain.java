import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class Block {
    int blockNumber;
    String creationTime;
    String userData;   
    String metadata;    
    String previousHash;
    String hash;

    public Block(int blockNumber, String userId, String password, String metadata, String previousHash) throws Exception {
        this.blockNumber = blockNumber;
        this.creationTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        this.userData = "UserID: " + userId + ", Password: " + password;
        this.metadata = metadata;
        this.previousHash = previousHash;
        this.hash = calculateHash();
    }

    private String calculateHash() throws Exception {
        String input = blockNumber + creationTime + userData + metadata + previousHash;
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = digest.digest(input.getBytes("UTF-8"));
        StringBuilder hexString = new StringBuilder();
        for (byte b : hashBytes) {
            String hex = Integer.toHexString(0xff & b);
            if(hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public void printBlock() {
        System.out.println("Block Number: " + blockNumber);
        System.out.println("Creation Time: " + creationTime);
        System.out.println("User Data: " + userData);
        System.out.println("Metadata (Cloud Path): " + metadata);
        System.out.println("Previous Hash: " + previousHash);
        System.out.println("Hash: " + hash);
        System.out.println("---------------------------");
    }
}

public class MiniBlockchainCustom {
    public static void main(String[] args) throws Exception {
        List<Block> blockchain = new ArrayList<>();

        // Block 1 (Genesis): "user001", password "abc123", path "/cloud/container/file1.txt"
        Block firstBlock = new Block(1, "user001", "abc123", "/cloud/container/file1.txt",
            "0000000000000000000000000000000000000000000000000000000000000000");
        blockchain.add(firstBlock);

        // Block 2: "user002", password "xyz789", path "/cloud/container/file2.txt"
        Block secondBlock = new Block(2, "user002", "xyz789", "/cloud/container/file2.txt", genesisBlock.hash);
        blockchain.add(secondBlock);

        // Print all blocks
        for (Block block : blockchain) {
            block.printBlock();
        }
    }
}
