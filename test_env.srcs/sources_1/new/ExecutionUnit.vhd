----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date: 04/21/2023 09:47:56 PM
-- Design Name: 
-- Module Name: ExecutionUnit - Behavioral
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

entity ExecutionUnit is
   Port (PCinc:in STD_LOGIC_VECTOR(15 downto 0);
      RD1:in STD_LOGIC_VECTOR(15 downto 0);
      RD2:in STD_LOGIC_VECTOR(15 downto 0);
      Ext_Imm:in STD_LOGIC_VECTOR(15 downto 0);
      func:in STD_LOGIC_VECTOR(2 downto 0);
      sa:in STD_LOGIC;
      ALUSrc:in STD_LOGIC;
      ALUOp:in STD_LOGIC_VECTOR(2 downto 0);
      BranchAddress:out STD_LOGIC_VECTOR(15 downto 0);
      ALURes:inout STD_LOGIC_VECTOR(15 downto 0);
      Zero:out STD_LOGIC );
end ExecutionUnit;

architecture Behavioral of ExecutionUnit is
signal ALUin2:STD_LOGIC_VECTOR(15 downto 0);
signal ALUCtrl:STD_LOGIC_VECTOR(2 downto 0);
signal ver1:STD_LOGIC_VECTOR(31 downto 0);
begin
--MUX 2:1
with ALUSrc select
ALUin2<=RD2 when '0',
        Ext_Imm when '1',
        (others=>'X') when others;
--ALU CONTROL
process(ALUOp,func)
begin
case ALUOp is
when "000" => --TIP R
   case func is
   when "000" =>ALUCtrl<="001";--add
   when "001"=>ALUCtrl<="000";--SRL
   when "010"=>ALUCtrl<="010";--MUL
   when "011"=>ALUCtrl<="011";--Sub
   when "100"=>ALUCtrl<="100";--AND
   when "101"=>ALUCtrl<="101";--OR
   when "110"=>ALUCtrl<="110";--XOR
   when "111"=>ALUCtrl<="111";--SLL 
   when others=>ALUCtrl<=(others=>'X');
   end case;
  when "001"=>ALUCtrl<="001";--ADDI
  when "010"=>ALUCtrl<="011";--SUBBI
  when "101"=>ALUCtrl<="100";--&
  when "100"=>ALUCtrl<="011";--beq
  when others=>ALUCtrl<=(others =>'X');
  end case;
  end process;
  --ALU
  process(ALUCtrl,ALUin2,RD1,sa)
  begin
  case ALUCtrl is
  when "000"=>ALURes<='0'&RD1(15 downto 1);
  when "001"=>ALURes<=RD1+ALUin2;
  when "010"=>ver1<=RD1*ALUin2;ALURes<=ver1(15 downto 0);
  when "011"=>ALURes<=RD1 - ALUin2;
  when "100"=>ALURes<=RD1 and ALUin2;
  when "101"=>ALURes<=RD1 or ALUin2;
  when "110"=>ALURes<=RD1 xor ALUin2;
  when "111"=>  ALURes<=RD1(14 downto 0) &'0';
  when others=>ALURes<=(others=>'0');
  end case;
  end process;
 process(ALURes)
 begin
 if(ALURes(0)='0' and ALURes(1)='0' and ALURes(2)='0' and ALURes(3)='0' and ALURes(4)='0' and ALURes(5)='0'and ALURes(6)='0'and ALURes(7)='0'and ALURes(8)='0' and ALURes(9)='0'and ALURes(10)='0' and ALURes(11)='0'and ALURes(12)='0'and ALURes(13)='0'and ALURes(14)='0'and ALURes(15)='0') then
 Zero<='1';
 else Zero<='0';
 end if;
 end process;
end Behavioral;
