----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date: 04/22/2023 02:05:32 PM
-- Design Name: 
-- Module Name: MainControl - Behavioral
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

entity MainControl is
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
end MainControl;

architecture Behavioral of MainControl is

begin
RegDst<='0';
ExtOp<='0';
ALUSrc<='0';
Branch<='0';
Jump<='0';
ALUOp<="000";
MemWrite<='0';
MemtoReg<='0';
RegWrite<='0';
process(Instruction)
begin
case Instruction is
when "000"=>RegDst<='1';RegWrite<='1';
when "001"=>ExtOp<='1';ALUSrc<='1';ALUOp<="001";RegWrite<='1';
when "010"=>ExtOp<='1';ALUSrc<='1';ALUOp<="001";MemtoReg<='1';RegWrite<='1';
when "011"=>ExtOp<='1';ALUSrc<='1';ALUOp<="001";memWrite<='1';
when "100"=>ExtOp<='1';Branch<='1';ALUOp<="010";
when "111"=>Jump<='1';
when others=>RegDst<='0';
ExtOp<='0';
ALUSrc<='0';
Branch<='0';
Jump<='0';
ALUOp<="000";
MemWrite<='0';
MemtoReg<='0';
RegWrite<='0';
end case;
end process;
end Behavioral;
