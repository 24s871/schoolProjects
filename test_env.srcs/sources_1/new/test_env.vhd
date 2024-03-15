----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date: 03/03/2023 09:45:12 AM
-- Design Name: 
-- Module Name: test_env - Behavioral
-- Project Name: 
-- Target Devices: 
-- Tool Versions: 
-- Description: 
-- 
-- Dependencies: 
-- 
-- Revision:
-- Revision 0.01 - File Created
-- Additional Comments:
-- 
----------------------------------------------------------------------------------


library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_ARITH.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;
use IEEE.NUMERIC_STD.ALL;

-- Uncomment the following library declaration if using
-- arithmetic functions with Signed or Unsigned values
--use IEEE.NUMERIC_STD.ALL;

-- Uncomment the following library declaration if instantiating
-- any Xilinx leaf cells in this code.
--library UNISIM;
--use UNISIM.VComponents.all;

entity test_env is
    Port ( clk : in STD_LOGIC;
           btn : in STD_LOGIC_VECTOR (4 downto 0);
           sw : in STD_LOGIC_VECTOR (15 downto 0);
           led : out STD_LOGIC_VECTOR (15 downto 0);
           an : out STD_LOGIC_VECTOR (3 downto 0);
           cat : out STD_LOGIC_VECTOR (6 downto 0));
           --digits:out STD_LOGIC_VECTOR(15 downto 0));
end test_env;

architecture Behavioral of test_env is
--signal suma:STD_LOGIC_VECTOR(15 downto 0);
--signal dif:STD_LOGIC_VECTOR(15 downto 0);
--signal right:STD_LOGIC_VECTOR(15 downto 0);
--signal left:STD_LOGIC_VECTOR(15 downto 0);
--signal iesire:STD_LOGIC_VECTOR(15 downto 0);
component MPG is
    port( input: in std_logic;
          clock:in std_logic;
          en:out std_logic);
     end component; 
     
component SSD is
         port( clk : in STD_LOGIC;
           an : out STD_LOGIC_VECTOR (3 downto 0);
           cat : out STD_LOGIC_VECTOR (6 downto 0);
           digits:in STD_LOGIC_VECTOR (15 downto 0));
          end component;
          
 component ROM is
 port(
 adresa :in STD_LOGIC_VECTOR (7 downto 0);
 rom_enable:in STD_LOGIC;
 data_output:out STD_LOGIC_VECTOR(15 downto 0);
 clock:in STD_LOGIC);
 end component;
          
 component REGFILE is
 port(
 RA1:in STD_LOGIC_VECTOR(3 downto 0);
 RA2:in STD_LOGIC_VECTOR(3 downto 0);
 clk:in STD_LOGIC;
 WA:in STD_LOGIC_VECTOR(3 downto 0);
 WD:in STD_LOGIC_VECTOR(3 downto 0);
 RD1:out STD_LOGIC_VECTOR(15 downto 0);
 RD2:out STD_LOGIC_VECTOR(15 downto 0));
 end component;
 
 component IFetch is
   Port (clk:in STD_LOGIC;
   en:in STD_LOGIC;
   rst:in STD_LOGIC;
   addr_branch:in STD_LOGIC_VECTOR(15 downto 0);
   addr_jump:in STD_LOGIC_VECTOR(15 downto 0);
   Jump:in STD_LOGIC;
   PCSrc:in STD_LOGIC;
   Instruction:out STD_LOGIC_VECTOR(15 downto 0);
   PCinc:out STD_LOGIC_VECTOR(15 downto 0) );
   
 end component;
 
 component IDecoder is
   Port (clk:in STD_LOGIC;
         Instruction:in STD_LOGIC_VECTOR(12 downto 0);
         en:in STD_LOGIC;
         WD:in STD_LOGIC_VECTOR(15 downto 0);
         RegWrite:in STD_LOGIC;
         RegDst:in STD_LOGIC;
         ExtOp:in STD_LOGIC;
         RD1:out STD_LOGIC_VECTOR(15 downto 0);
         RD2:out STD_LOGIC_VECTOR(15 downto 0);
         Ext_Imm:out STD_LOGIC_VECTOR(15 downto 0);
         func:out STD_LOGIC_VECTOR(2 downto 0);
         sa:out STD_LOGIC);
 end component;
 
 component ExecutionUnit is
 Port(PCinc:in STD_LOGIC_VECTOR(15 downto 0);
      RD1:in STD_LOGIC_VECTOR(15 downto 0);
      RD2:in STD_LOGIC_VECTOR(15 downto 0);
      Ext_Imm:in STD_LOGIC_VECTOR(15 downto 0);
      func:in STD_LOGIC_VECTOR(2 downto 0);
      sa:in STD_LOGIC;
      ALUSrc:in STD_LOGIC;
      ALUOp:in STD_LOGIC_VECTOR(2 downto 0);
      BranchAddress:out STD_LOGIC_VECTOR(15 downto 0);
      ALURes:inout STD_LOGIC_VECTOR(15 downto 0);
      Zero:out STD_LOGIC);
 end component;
 
 component MainControl is
 Port(Instruction:in STD_LOGIC_VECTOR(15 downto 13);
      RegDst:out STD_LOGIC;
      ExtOp:out STD_LOGIC;
      ALUSrc:out STD_LOGIC;
      Branch:out STD_LOGIC;
      Jump:out STD_LOGIC;
      ALUOp:out STD_LOGIC_VECTOR(2 downto 0);
      MemWrite:out STD_LOGIC;
      MemtoReg:out STD_LOGIC;
      RegWrite:out STD_LOGIC);
 end component;
 
 component MEM is
 Port(clk:in STD_LOGIC;
  en:in STD_LOGIC;
  ALUResIn:in STD_LOGIC_VECTOR(15 downto 0);
  RD2:in STD_LOGIC_VECTOR(15 downto 0);
  MemWrite:in STD_LOGIC;
  MemData:out STD_LOGIC_VECTOR(15 downto 0);
  ALUResOut:out STD_LOGIC_VECTOR(15 downto 0));
  end component;
  
  component IFetch_pipe is
    Port (clk:in STD_LOGIC;
    en:in STD_LOGIC;
    rst:in STD_LOGIC;
    addr_branch:in STD_LOGIC_VECTOR(15 downto 0);
    addr_jump:in STD_LOGIC_VECTOR(15 downto 0);
    Jump:in STD_LOGIC;
    PCSrc:in STD_LOGIC;
    Instruction:out STD_LOGIC_VECTOR(15 downto 0);
    PCinc:out STD_LOGIC_VECTOR(15 downto 0));
     --PCinc_IF_ID:out std_logic_vector(15 downto 0));
     --Instruction_IF_ID:out std_logic_vector(15 downto 0));
  end component;
  component IDecoder_pipe is
    Port (clk:in STD_LOGIC;
          Instruction_pipe:in STD_LOGIC_VECTOR(15 downto 0);
          en:in STD_LOGIC;
          WD:in STD_LOGIC_VECTOR(15 downto 0);
          RegWrite:in STD_LOGIC;
          ExtOp:in STD_LOGIC;
          RD1:out STD_LOGIC_VECTOR(15 downto 0);
          RD2:out STD_LOGIC_VECTOR(15 downto 0);
          Ext_Imm:out STD_LOGIC_VECTOR(15 downto 0);
          func:out STD_LOGIC_VECTOR(2 downto 0);
          sa:out STD_LOGIC;
          rd:out std_logic_vector(2 downto 0);
          rt:out std_logic_vector(2 downto 0));
  end component;
 component ExecutionUnit_pipe is
    Port (PCinc:in STD_LOGIC_VECTOR(15 downto 0);
        RD1:in STD_LOGIC_VECTOR(15 downto 0);
        RD2:in STD_LOGIC_VECTOR(15 downto 0);
        Ext_Imm:in STD_LOGIC_VECTOR(15 downto 0);
        func:in STD_LOGIC_VECTOR(2 downto 0);
        sa:in STD_LOGIC;
        rt:in std_logic_vector(2 downto 0);
        rd:in std_logic_vector(2 downto 0);
        ALUSrc:in STD_LOGIC;
        RegDst:in STD_LOGIC;
        BranchAddress:out STD_LOGIC_VECTOR(15 downto 0);
        ALURes:inout STD_LOGIC_VECTOR(15 downto 0);
        rWA:out std_logic_vector(2 downto 0);
        Zero:out STD_LOGIC;
        ALUOp:in STD_LOGIC_VECTOR(2 downto 0));
  end component;

  
  --signal ce:STD_LOGIC;
  --signal cnt:STD_LOGIC_VECTOR(7 downto 0):=(others => '0');
  --signal enable:STD_LOGIC;
  --signal do:STD_LOGIC_VECTOR(15 downto 0);
  
  signal Instruction,PCinc,sum,RD1,RD2,Ext_Imm,Ext_func,Ext_sa:STD_LOGIC_VECTOR(15 downto 0);
  signal func:STD_LOGIC_VECTOR(2 downto 0);
  signal rt,rd,rWA:std_logic_vector(2 downto 0);
  signal sa,Zero:STD_LOGIC;
  signal digits:STD_LOGIC_VECTOR(15 downto 0);
  signal en,rst,PCSrc:STD_LOGIC;
  signal RegDst,ExtOp,ALUSrc,Branch,Jump,MemWrite,MemtoReg,RegWrite:STD_LOGIC;
  signal ALUOp:STD_LOGIC_VECTOR(2 downto 0);
  signal JumpAddress,BranchAddress,ALURes,ALURes1,MemData:STD_LOGIC_VECTOR(15 downto 0);
  --pipeline
  --IF-ID
  signal PCinc_IF_ID,Instruction_IF_ID:std_logic_vector(15 downto 0);
  --ID_EX
  signal PCinc_ID_EX,RD1_ID_EX,RD2_ID_EX,Ext_Imm_ID_EX:std_logic_vector(15 downto 0);
  signal func_ID_EX,rt_ID_EX,rd_ID_EX,ALUOP_ID_EX:std_logic_vector(2 downto 0);
  signal sa_ID_EX,MemToReg_ID_EX,RegWrite_ID_EX,MemWrite_ID_EX,Branch_ID_EX,ALUSrc_ID_EX,RegDst_ID_EX:std_logic;
  --EX_MEM
  signal BranchAdress_EX_MEM,ALURes_EX_MEM,RD2_EX_MEM:std_logic_vector(15 downto 0);
  signal rd_EX_MEM:std_logic_vector(2 downto 0);
  signal Zero_EX_MEM,MemToReg_EX_MEM,RegWrite_EX_MEM,MemWrite_EX_MEM,Branch_EX_MEM:std_logic;
  --MEM_WB
  signal MemData_MEM_WB,ALURes_MEM_WB:std_logic_vector(15 downto 0);
  signal rd_MEM_WB:std_logic_vector(2 downto 0);
  signal MemToReg_MEM_WB,RegWrite_MEM_WB:std_logic;
  
begin
debouncer1:MPG port map(en=>en,input=>btn(0),clock=>clk);
debouncer2:MPG port map(en=>rst,input=>btn(1),clock=>clk);
--single-cycle
inst_IF:IFetch port map(clk=>clk,en=>en,rst=>rst,addr_branch=>BranchAddress,addr_jump=>JumpAddress,Jump=>Jump,PCSrc=>PCSrc,Instruction=>Instruction,PCinc=>PCinc);
inst_ID:IDecoder port map(clk=>clk,en=>en,Instruction(12 downto 0)=>Instruction,WD=>sum,RegWrite=>RegWrite,RegDst=>RegDst,ExtOp=>ExtOp,RD1=>RD1,RD2=>RD2,Ext_Imm=>Ext_Imm,func=>func,sa=>sa);
inst_MC:MainControl port map(Instruction(15 downto 13)=>Instruction,RegDst=>RegDst,ExtOp=>ExtOp,ALUSrc=>ALUSrc,Branch=>Branch,Jump=>Jump,ALUOp=>ALUOp,MemWrite=>MemWrite,MemtoReg=>MemtoReg,RegWrite=>RegWrite);
inst_Ex:ExecutionUnit port map(PCinc=>PCinc,RD1=>RD1,RD2=>RD2,Ext_Imm=>Ext_Imm,func=>func,sa=>sa,ALUSrc=>ALUSrc,ALUOp=>ALUOp,BranchAddress=>BranchAddress,ALURes=>ALURes,Zero=>Zero);
inst_Mem:MEM port map(clk=>clk,en=>en,ALUResIn=>ALURes,RD2=>RD2,MemWrite=>MemWrite,MemData=>MemData,ALUResOut=>ALURes1);
--pipeline
--inst_IF2:IFetch_pipe port map(clk=>clk,en=>en,rst=>rst,addr_branch=>BranchAdress_EX_MEM,addr_jump=>JumpAddress,Jump=>Jump,PCSrc=>PCSrc,Instruction=>Instruction,PCinc=>PCinc);
--inst_ID2:IDecoder_pipe port map(clk=>clk,Instruction_pipe=>Instruction_IF_ID,en=>en,WD=>sum,RegWrite=>RegWrite_MEM_WB,ExtOp=>ExtOp,RD1=>RD1,RD2=>RD2,Ext_Imm=>Ext_Imm,func=>func,sa=>sa,rt=>Instruction(9 downto 7),rd=>Instruction(12 downto 10));
--inst_MC2:MainControl port map(Instruction=>Instruction_IF_ID(15 downto 13),RegDst=>RegDst,ExtOp=>ExtOp,ALUSrc=>ALUSrc,Branch=>Branch,Jump=>Jump,ALUOp=>ALUOp,MemWrite=>MemWrite,MemtoReg=>MemtoReg,RegWrite=>RegWrite);
--inst_EX2:ExecutionUnit_pipe port map (PCinc=>PCinc_ID_EX,RD1=>RD1_ID_EX,RD2=>RD2_ID_EX,Ext_Imm=>Ext_Imm_ID_EX,func=>func_ID_EX,sa=>sa_ID_EX,rt=>rt_ID_EX,rd=>rd_ID_EX,ALUSrc=>ALUSrc_ID_EX,RegDst=>RegDst_ID_EX,BranchAddress=> BranchAddress,ALURes=>ALURes,Zero=> Zero,rWA=>rd_EX_MEM,ALUOp=>ALUOp_ID_EX);
--inst_MEM2:MEM port map(clk=>clk,en=>en,ALUResIn=>ALURes_EX_MEM,RD2=>RD2_EX_MEM,MemWrite=>MemWrite_EX_MEM,MemData=>MemData,ALUResOut=>ALURes1);
--Instruction_pipe(12 downto 10)=>rd_MEM_WB(in idecode_pipe)(!don't uncomment)
display:SSD port map(clk=>clk,an=>an,cat=>cat,digits=>digits);

--write back,syngle cycle
with MemtoReg select
sum<=MemData when '1',
    ALURes1 when '0',
    (others=>'X') when others;
--branch control,syncgle cycle
PCSrc<=Zero and Branch;
--jump address,single cycle
JumpAddress<=PCinc(15 downto 13)&Instruction(12 downto 0); 

--sum<=RD1+RD2;(!)
--Ext_func<="0000000000000"&func;(!)
--Ext_sa<="000000000000000"&sa;(!)

--with sw(7) select(!)
--digits<=PCinc when '1',(!)
--        Instruction when '0',(!)
--        (others =>'X') when others;(!)

with sw(7 downto 5) select
 digits<=Instruction when "000",
        PCinc when "001",
         RD1 when "010",
         RD2 when "011",
         Ext_Imm when "100",
         ALURes when "101",
        MemData when "110",
         sum when "111",
         (others=>'X') when others;
        
 --main control
 led(10 downto 0)<=ALUOp & RegDst&ExtOp&ALUSrc&Branch&Jump&MemWrite&MemtoReg&RegWrite;

--WRITE BACK PIPELINE
--with MemtoReg_MEM_WB select
--sum<=MemData_MEM_WB when '1',
--  ALURes_MEM_WB when '0',
--  (others=>'X') when others;
--BRANCH CONTROL PIPELINE
--PCSrc<=Zero_EX_MEM and Branch_EX_MEM;
--JUMP ADDRESS PIPELINE
--JumpAddress<=PCinc_IF_ID(15 downto 13) & Instruction_IF_ID(12 downto 0);
--PIPELINE REGISTERS
--process(clk)
--begin
--if rising_edge(clk) then
--if en='1' then
--IF_ID
--PCinc_IF_ID<=PCinc;
--Instruction_IF_ID<=Instruction;
--ID_EX
--PCinc_ID_EX<=PCinc_IF_ID;
--RD1_ID_EX<=RD1;
--RD2_ID_EX<=RD2;
--Ext_Imm_ID_EX<=Ext_Imm;
--sa_ID_EX<=sa;
--func_ID_EX<=func;
--rt_ID_EX<=Instruction_IF_ID(12 downto 10);
--rd_ID_EX<=Instruction_IF_ID(9 downto 7);
--MemToReg_ID_EX<=MemtoReg;
--RegWrite_ID_EX<=RegWrite;
--MemWrite_ID_EX<=MemWrite;
--Branch_ID_EX<=Branch;
--ALUSrc_ID_EX<=ALUSrc;
--ALUOp_ID_EX<=ALUOp;
--RegDst_ID_EX<=RegDst;
--EX_MEM
--BranchAdress_EX_MEM<=BranchAddress;
--Zero_EX_MEM<=Zero;
--ALURes_EX_MEM<=ALURes;
--RD2_EX_MEM<=RD2_ID_EX;
--rd_EX_MEM<=rWA;
--MemToReg_EX_MEM<=MemToReg_ID_EX;
--RegWrite_EX_MEM<=RegWrite_ID_EX;
--MemWrite_EX_MEM<=MemWrite_ID_EX;
--Branch_EX_MEM<=Branch_ID_EX;
--MEM_WB
--MemData_MEM_WB<=MemData;
--ALURes_MEM_WB<=ALURes1;
--rd_MEM_WB<=rd_EX_MEM;
--MemToReg_MEM_WB<=MemToReg_EX_MEM;
--RegWrite_MEM_WB<=RegWrite_EX_MEM;
--end if;
--end if;
--end process;
--SSD DISPLAY MUX PIPELINE
--with sw(7 downto 5) select
--digits<=Instruction when "000",
--          PCinc when "001",
--          RD1_ID_EX when "010",
--          RD2_ID_EX when "011",
--          Ext_Imm_ID_EX when "100",
--          ALURes when "101",
--          MemData when "110",
--          sum when "111",
--   (others=>'X') when others;

end Behavioral;
