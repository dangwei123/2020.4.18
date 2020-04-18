public class Server {
    private static ExecutorService poll=Executors.newCachedThreadPool();
    public static void main(String[] args) throws IOException {
        ServerSocket server=new ServerSocket(9000);
        while(true){
            Socket client=server.accept();
            poll.execute(new Task(client));
        }
    }
}

 class Task implements Runnable{
    private Socket client;
    public Task(Socket client){
        this.client=client;
    }
     @Override
     public void run() {
         try {
             BufferedReader br=new BufferedReader(
                     new InputStreamReader(client.getInputStream())
             );
             PrintWriter pw=new PrintWriter(client.getOutputStream(),true);
             String s;
             int i=0;
             while((s=br.readLine())!=null){
                 System.out.println(s);
                 pw.println("赞同"+i);
                 i++;
             }
         } catch (IOException e) {
             e.printStackTrace();
         }
     }
 }


public class Client {
    private static final String PORT="127.0.0.1";
    public static void main(String[] args) throws IOException {
        Socket client=new Socket(PORT,9000);
        BufferedReader br=new BufferedReader(
                new InputStreamReader(client.getInputStream())
        );
        PrintWriter pw=new PrintWriter(client.getOutputStream(),true);
        Scanner sc=new Scanner(System.in);
        new Thread(()->{
            while(sc.hasNextLine()){
                String s=sc.nextLine();
                pw.println(s);
            }
        }).start();

        String str;
        while((str=br.readLine())!=null){
            System.out.println(str);
        }
    }
}


给定一个整数矩阵，找出最长递增路径的长度。

对于每个单元格，你可以往上，下，左，右四个方向移动。 你不能在对角线方向上移动或移动到边界外（即不允许环绕）。

class Solution {   
    private int row;
    private int col;
    public int longestIncreasingPath(int[][] matrix) {
        int max=0;
        row=matrix.length;
        if(row==0) return 0;
        col=matrix[0].length;
        int[][] visit=new int[row][col];
        for(int i=0;i<row;i++){
            for(int j=0;j<col;j++){
                max=Math.max(max,dfs(matrix,i,j,Integer.MIN_VALUE,visit));
            }
        }
        return max;
    }
    private int dfs(int[][] matrix,int i,int j,int target,int[][] v){
        if(i<0||j<0||i>=row||j>=col||matrix[i][j]<=target){
            return 0;
        }
        if(v[i][j]>0){
            return v[i][j];
        }
        int t=0;
        t=Math.max(t,dfs(matrix,i+1,j,matrix[i][j],v));
        t=Math.max(t,dfs(matrix,i-1,j,matrix[i][j],v));
        t=Math.max(t,dfs(matrix,i,j+1,matrix[i][j],v));
        t=Math.max(t,dfs(matrix,i,j-1,matrix[i][j],v));
        v[i][j]=1+t;
        return 1+t;
    }
}

给定一个整数数组 nums，按要求返回一个新数组 counts。数组 counts 有该性质： counts[i] 的值是  nums[i] 右侧小于 nums[i] 的元素的数量。

来源：力扣（LeetCode）
链接：https://leetcode-cn.com/problems/count-of-smaller-numbers-after-self
著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
class Solution {
    public List<Integer> countSmaller(int[] nums) {
        List<Integer> sort=new ArrayList<>();
        int[] res=new int[nums.length];
        for(int i=nums.length-1;i>=0;i--){
            int left=0;
            int right=sort.size();
            while(left<right){
                int mid=left+(right-left)/2;
                if(sort.get(mid)<nums[i]){
                    left=mid+1;
                }else{
                    right=mid;
                }
            }
            res[i]=left;
            sort.add(left,nums[i]);
        }
        List<Integer> list=new ArrayList<>();
        for(int i=0;i<res.length;i++){
            list.add(res[i]);
        }
        return list;
    }
}